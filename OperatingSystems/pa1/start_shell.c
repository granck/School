#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>

//Shell commands
int shell_cd(char **args);
int shell_help(char **args);
int shell_exit(char **args);

char *builtin_str[] = {
	"cd",
	"help",
	"exit"
};

int(*builtin_func[]) (char **) = {
	&shell_cd,
	&shell_help,
	&shell_exit
};

int shell_builtin_count(){
	return sizeof(builtin_str) / sizeof(char *);
}//end function builtin_count

int shell_cd(char **args){
	
	//no argument for change directory command
	if(args[1] == NULL){
		perror("Error, expecting directory for \"cd\"\n");
	}
	//error performing change directory command
	else{
		if(chdir(args[1]) != 0){
			perror("Error changing directories.\n");
		}
	}
	return 1;

}//end function shell_cd

int shell_help(char **args){
	int i;
	printf("Beta Shell. Basic Implementation.\n");
	printf("Built in commands.\n");
	for(i = 0; i < shell_builtin_count(); i++){
		printf(" %s\n",  builtin_str[i]);
	}//end for

	return 1;
	
}//end fucntion shell_help

int shell_exit(char **args){
	return 0;	

}//end function shell_exit

int shell_loop_launch(char **args, int background){
	
	pid_t pid, wpid;
	int status;
	
	pid = fork();

	//child process
	if(pid == 0){
		if(execvp(args[0], args) == -1){
			perror("Error executing child process, exiting\n");
		}
		exit(EXIT_FAILURE);
	}//end child

	if(pid < 0){
		perror("Error forking to child process.\n");
	}
	
	//parent waits for child process if command doesn't specify
	if(background == 0){
		do{
			wpid = waitpid(pid, &status, WUNTRACED);

		//parent function waits till child exits or gets killed from a signal
		} while (!WIFEXITED(status) && !WIFSIGNALED(status)); 	
	}
	//else command used '&' signalling not to wait for child to finish
	else{
		printf("Child process running in background, PID: %d\n", pid);
	}
	
	return 1; //signals calling function to accept input again
}//end function launch

void IOCommand(char *args[], char* inputFile, char* outputFile, int option){
	
	int err = -1;

	int fileDescriptor;
	pid_t pid, wpid;

	pid = fork();

	if(pid < 0){
		perror("Error executing child process, exiting\n");
		return;
	}

	//output redirection
	if(pid == 0){
		
		//command line reads: "command > output file"
		if(option == 0){
			fileDescriptor = open(outputFile, O_CREAT | O_TRUNC | O_WRONLY, 0600);
			
			//standard output changed to go into fileDescriptor
			dup2(fileDescriptor, STDOUT_FILENO);
			//close pipe since no more data will be sent afterward
			close(fileDescriptor);
		}
		
		//command line reads: command < input file > output file
		else if(option == 1){
			fileDescriptor = open(inputFile, O_RDONLY, 0600);

			//standard input replaced to feed from fileDescriptor
			dup2(fileDescriptor, STDIN_FILENO);
			//close pipe since no more data will be recieved afterwards
			close(fileDescriptor);

			//Now we need to redirect output from command that used inputFile
			//Will redirect to outputFile
			fileDescriptor = open(outputFile, O_CREAT | O_TRUNC | O_WRONLY, 0600);
			dup2(fileDescriptor, STDOUT_FILENO);
			close(fileDescriptor);

		}
		//command line reads: command < input file
		else if(option == 2){
			fileDescriptor = open(inputFile, O_RDONLY, 0600);

			//standard input replaced to feed from fileDescriptor
			dup2(fileDescriptor, STDIN_FILENO);
			//close pipe since no more data will be recieved afterwards
			close(fileDescriptor);

		}
		if(execvp(args[0], args) == err){
			printf("err");
			kill(getpid(), SIGTERM);
		}

	}
	wpid = waitpid(pid, NULL, 0);

}//end function IOCommand

void pipeCommand(char *args[]){
	
	//file descriptors for pipes
	//2 pipes ensures ability to pass through multiple pipe commands
	int fileds1[2];
	int fileds2[2];

	int command_count = 0; //number of commands to account
	char *command[256]; //array holding all elements of args for 1 specific command

	pid_t pid, wpid;

	int err = -1;
	int end = 0;

	//loop variable counters
	int i = 0;
	int j = 0;
	int k = 0;
	int l = 0;

	//Determine number of commands 
	while(args[l] != NULL){
		if(strcmp(args[l], "|") == 0){
			command_count++; //Found pipe, increase command count
		}
		l++;

	}
	command_count++; //while loop ends with command_count -1,  need to add 1

	//configure pipes for each set of commands and
	//perform said commands
	//Loop repeats until there are no more commands
	while(args[j] != NULL && end != 1){
		k = 0; //resets on each new command to store new elements into command array
		
		while(strcmp(args[j], "|") != 0){
			command[k] = args[j]; //adds element from args to command
			j++;
			if(args[j] == NULL){
				end = 1;
				k++;
				break;
			}
			k++;
		}

		command[k] = NULL; //last element for current command is null to signal end
		j++;

		//pipes will behave differently depending on command number
		if(i % 2 != 0){
			pipe(fileds1); //odd command number
		}
		else{
			pipe(fileds2); //even command number
		}

		pid = fork();

		if(pid < 0){
			if(i != command_count -1){
				if(i % 2 != 0){
					close(fileds1[1]); //odd
				}
				else{
					close(fileds2[1]); //even
				}

			}
			perror("Failed to fork child process\n");
			return;
		}
		if(pid == 0){
			//first command 
			if(i == 0){
				dup2(fileds2[1], STDOUT_FILENO);
			}
			//last command
			else if(i == command_count - 1){
				if(command_count % 2 != 0){ 
					dup2(fileds1[0], STDIN_FILENO); //odd
				}
				else{ 
					dup2(fileds2[0], STDIN_FILENO); //even
				}
			}
			//any command in the middle
			else{
				if(i % 2 != 0){
					dup2(fileds2[0], STDIN_FILENO); //odd
					dup2(fileds1[1], STDOUT_FILENO);
				}
				else{
					dup2(fileds1[0], STDIN_FILENO); //even
					dup2(fileds2[1], STDOUT_FILENO);
				}
			}
			if(execvp(command[0], command) == err){
				kill(getpid(), SIGTERM);
			}
		}

		//closing pipes
		//first command
		if(i == 0){
			close(fileds2[1]);
		}
		//last command
		else if(i == command_count -1){
			if(command_count % 2 != 0){
				close(fileds1[0]);
			}
			else{
				close(fileds2[0]);
			}
		}
		//any command in the middle
		else{
			if(i % 2 != 0){
				close(fileds2[0]);
				close(fileds1[1]);
			}
			else{
				close(fileds1[0]);
				close(fileds2[1]);
			}
		}

		wpid = waitpid(pid, NULL, 0);

		i++; //next loop starts with first command after pipe
	}

}//end function pipeCommand

int shell_loop_execute(char **args){
	int i = 0;
	int j = 0;
	int k = 0;

	int fileDescriptor; //used if streaming is required
	int standardOut;

	int aux;
	int background = 0;

	char *args_aux[256];

	//empty command from command line
	if(args[0] == NULL)
		return 1;
	//check if command is builtin
	for(k = 0; k < shell_builtin_count(); k++){

		if(strcmp(args[0], builtin_str[k]) == 0){
			return (*builtin_func[k])(args);
		}
	}
	//Check for special characters
	//if found, places it in args_aux array
	while(args[j] != NULL){
		if((strcmp(args[j], ">") == 0) || strcmp(args[j], "<") == 0 || (strcmp(args[j], "&")) == 0){
			break;	
		}
		args_aux[j] = args[j];
		j++;

	}//end while

	//without this, commands trail and try to access restricted memory
	args_aux[j] = NULL;
	
	//Command: pwd, check whether output is being redirected or if simply printing to command line
	if(strcmp(args[0], "pwd") == 0){
		char *currentDirectory;
		if(args[j] != NULL){
			//if sending output to a file
			if((strcmp(args[j], ">") == 0) && (args[j+1] != NULL)){
				fileDescriptor = open(args[j+1], O_CREAT | O_TRUNC | O_WRONLY, 0600);
				//alter standard output to file input
				standardOut = dup(STDOUT_FILENO);
				dup2(fileDescriptor, STDOUT_FILENO);
				close(fileDescriptor);
				printf("%s\n", getcwd(currentDirectory, 1024));
				dup2(standardOut, STDOUT_FILENO);
			}
		}
		else{
			printf("%s\n", getcwd(currentDirectory, 1024));
		}
	}

		//Determine if Piping, I/O redirection, etc are needed
	else{
		while(args[i] != NULL && background == 0){
			
			if(strcmp(args[i], "&") == 0){
				background = 1;
			}
			
			//Check for pipe command
			else if(strcmp(args[i], "|") == 0){
				pipeCommand(args);
				return 1;
			}
			
			//IO '<'
			else if(strcmp(args[i], "<") == 0){
				aux = i+1;

				//Check if there is only input file
				//command < input file
				if(args[aux] != NULL && args[aux + 1] == NULL){
					IOCommand(args_aux, args[i + 1], NULL, 2);
					return 1;

				}
				//checking if there is input file, second stream symbol, second file to output
				//**Doesn't actually check if correct values, just if there are enough arguments
				//command < input file > output file
				else if(args[aux] == NULL || args[aux+1] == NULL || args[aux+2] == NULL){
					printf("Requires more inputs\n");
					return -1;
				}
				//if all conditions above are satisfied, check if there is second stream
				//to send ouput from first command
				else{
					if(strcmp(args[aux+1], ">") != 0){
						printf("Expecting '>', but got %s\n", args[aux+1]);
						return -2;
					}
				}
				
				IOCommand(args_aux, args[i+1],args[i+3], 1);
				return 1;

			}
			
			//IO '>'
			else if(strcmp(args[i], ">") == 0){
				//check if there is an output file
				//command > output file
				if(args[i+1] == NULL){
					printf("Requires more inputs\n");
					return -1;
				}
				IOCommand(args_aux, NULL, args[i+1], 0);
				return 1;

			}

			i++; //increments counter to compare next element in array of command line input
		}
		//launch command normally if it doesn't fit into above criteria
		shell_loop_launch(args_aux, background);

	}
	return 1;
}//end function execute

#define BUFFER_SIZE 64
#define TOKEN_DELIMETER " \t\r\n\a"
char **shell_loop_split_line(char *line){

	int buffer_size = BUFFER_SIZE, position = 0; //position in array
	char **tokens = malloc(buffer_size * sizeof(char*)); //array of pointers to character string line
	char *token; //individual token returned with strtok function
	
	//if error with malloc, returns NULL
	//!NULL = true
	if(!tokens){
		perror("Error with malloc command, allocation failed\n");
		exit(EXIT_FAILURE);
	}
	
	token = strtok(line, TOKEN_DELIMETER);
	while(token != NULL){
		tokens[position] = token;
		position ++;
	
		if(position >= buffer_size){
			buffer_size += BUFFER_SIZE;
			tokens = realloc(tokens, buffer_size * sizeof(char*));

			//if error, realloc returns NULL
			//!NULL = true
			if(!tokens){
				perror("Error with realloc command, re-allocation failed\n");
				exit(EXIT_FAILURE);
			}//end inner if
		}//end outer if

		token = strtok(NULL, TOKEN_DELIMETER);
	}//end while

	tokens[position] = NULL;
	return tokens;
		

}//end function split_line

char *shell_loop_read_line(void){
	
	char *line = NULL;
	size_t bufsize = 0;;

	//reads line from standard input
	//exits if fails to read
	if(getline(&line, &bufsize, stdin) == -1){
		perror("Error with getline command, reading line failed.\n");
		exit(EXIT_FAILURE);
	}
	//returns read line
	return line;

}//end function read_line

void shell_loop(void){

	char *line;
	char **args;
	int status;

	do{
		char *directory_buffer = NULL;
		size_t buffer_size = 0;
		char *current_directory;

		current_directory = getcwd(directory_buffer, buffer_size);

		printf("%s > ", current_directory);
		line = shell_loop_read_line();
		args = shell_loop_split_line(line);
		status = shell_loop_execute(args);

		free(line);
		free(args);

	} while(status);


}//end shell_loop function


int main(int argc, char **argv){

	//Load config files

	//Run loop
	shell_loop();

	//Shutdown

	return EXIT_SUCCESS;

}//end main
