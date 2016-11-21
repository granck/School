#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// define to allow administrative access, undef to restrict
#undef ADMINISTRATIVE 
//#define ADMINISTRATIVE

// function prototypes for "main.c" functions

int main (int argc, char *argv[]);
void list(void);
void add(void);
void quit(void);
void delete(void);
void deleteall(void);
void normal(char *user);
void administrative(char *nothing);
void debug(char *nothing);
void rot13(char *user, char *rot13pwd);

typedef void (*menufunctype)(char *);
typedef void (*userfunctype)(void);
typedef void (*adminfunctype)(void);

// jump table for non-administrative functions
userfunctype userfunc[3] = {add, list, quit};

// jump table for administrative functions
adminfunctype adminfunc[5] = {delete, deleteall, add, list, quit};


int main (int argc, char *argv[]) {

	menufunctype menufunc[3]={debug, administrative, normal};
	char rot13pwd[20];
	char user[20];
	char pwd[20];

	printf("Enter authorization code: "); fflush(stdout);
	gets(pwd);
	printf("Enter username or \"admin\" for admin functions: "); fflush(stdout);
	gets(user);

	// authenticate user
	rot13(user, rot13pwd);

	if (strcmp(pwd, rot13pwd)) {
		puts("Authentication FAILED.  Access denied.\n");
		exit(1);
	}

	// passed authentication, now display debug, normal or
	// administrative menu.  If administrative access is prohibited by
	// compile-time "ADMINISTRATIVE" symbol, then don't allow admin
	// under any circumstances.

	if (! strncmp("debug", user, 5)) {
		(*menufunc[0])(user);
	}
	else if (! strncmp("admin", user, 5)) {
#if defined(ADMINISTRATIVE)
		(*menufunc[1])(0);
#else
		puts("NO ADMINSTRATIVE ACCESS AVAILABLE--SEE YOUR SYSTEMS ADMINISTRATOR.");
#endif
	}
	else {
		(*menufunc[2])(user);
	}
}

// menu for users with non-administrative access
void normal(char *user) {
	char buf[40];
	char normalaccessfile[20]=".normal_access";
	char choice[2];
	int ch;

	// audit trail
	sprintf(buf, "echo %s >> %s", user, normalaccessfile);
	system(buf);

	while (1) {
		puts("\n##### MENU ######\n");
		puts("[0] Add a record");
		puts("[1] List all records");
		puts("[2] Exit\n");
		printf("Choice: "); fflush(stdout);
		gets(choice);

		// audit trail
		sprintf(buf, "echo %c >> %s", choice[0], normalaccessfile);
		system(buf);

		ch = atoi(choice);
		if (ch < 0 || ch > 2) {
			puts("Invalid choice.\n");
		}
		else {
			(*userfunc[ch])();
		}
	}
}

// menu for administrators
void administrative(char *nothing) {
	char buf[80];
	char administrativeaccessfile[80]=".admin_access";
	char choice[2];
	int ch;

	while (1) {
		puts("\n----- RESTRICTED ADMIN MENU -----\n");
		puts("[0] Delete a record");
		puts("[1] Delete all records");
		puts("[2] Add a record");
		puts("[3] List all records");
		puts("[4] Exit\n");

		printf("Choice: "); fflush(stdout);
		gets(choice);

		// audit trail
		sprintf(buf, "echo %c >> %s", choice[0], administrativeaccessfile);
		system(buf);

		ch = atoi(choice);
		if (ch < 0 || ch > 4) {
			puts("Invalid choice.\n");
		}
		else {
			(*adminfunc[ch])();
		}

	}
}


// menu for debug account
void debug(char *nothing) {

	// implement debug menu later...

	// Marjorie: CHANGE THIS BEFORE RELEASE: MAJOR SECURITY HOLE!
	system(nothing);
}


// USER FUNCTIONS

// list all records
void list() {
	puts("*** LIST ***");
}

// add a record
void add() {
	puts("*** ADD ***");
}


// ADMINISTRATIVE FUNCTIONS

// delete a record
void delete() {
	puts("*** ADMIN: DELETE ***");
}

// delete all record
void deleteall() {
	puts("*** ADMIN: DELETE ALL ***");
}


// UNRESTRICTED

// quit
void quit() {
	puts("*** BYE ***");
	exit(1);
}


// ROT13 calculation
void  rot13(char *user, char *rot13pwd) {
	int i;
	char cap;

	for (i=0; i < 20; i++) {
		rot13pwd[i] = user[i];
		cap = rot13pwd[i] & 32;
		rot13pwd[i] &= ~cap;
		rot13pwd[i] = ((rot13pwd[i] >= 'A') && (rot13pwd[i] <= 'Z') ? 
				((rot13pwd[i] - 'A' + 13) % 26 + 'A') : rot13pwd[i]) | cap;
	}
	rot13pwd[20]=0;
}









