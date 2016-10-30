import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class randomCombo{

	
	public static void main(String[] args){
		
		String allSkills[] = {"1693474380",
			"1693474381",
				"1693474382",
				"6292528840",
				"6292528841",
				"6292528842",
				"2199384300",
				"2199384301",
				"2199384302",
				"9241478170",
				"9241478171",
				"9241478172",
				"8247341210",
				"8247341211",
				"8247341212",
				"9090234520",
				"9090234521",
				"9090234522",
				"2010203450",
				"2010203451",
				"2010203452",
				"5124367130",
				"5124367131",
				"5124367132",
				"8493764910",
				"8493764911",
				"3458901231",
				"3458901232",
				"9281736450",
				"9281736451",
				"9281736452",
				"8371065340",
				"8371065341",
				"1112223331",
				"1112223332",
				"3344223341",
				"3344223342",
				"6262628490",
				"6262628491",
				"6262628492"};

		String course_codes[] = {"43911", "34774", "65503", "27151", "18367", "40062", 
			"56737", "38268", "58278", "47479", "75118", "38923", "43003", "17106",
			"35043", "21640", "96355", "64240", "26875", "79109", "74114", "35923",
			"24824", "96050", "58300"};


	
		String personId[] = {"1234567890", "987654321", "1111111111", "2222222222",
			"3412569078", "9182736450", "7701488321", "4444444444", "5555555555",
			"6969696969", "6666666666", "4545454545", "8105432678", "2580134679",
			"6543290133", "2299447755"};

		String pos_code[] = {"1234567890", "2341567912", "5432198760", "8749348561",
			"4567123456", "7491036712", "1230987654", "3489120754", "1230123012",
			"2200440066"};
		int printInsert = 2;	
		if(printInsert == 0){
			Random random = new Random();
			int numberOfStatements = 0;
			for(int x = 0; x < personId.length; x++){
				ArrayList<String> skillsForPerson = new ArrayList<String>();
				while(skillsForPerson.size() < 7){
					String randomSkill = allSkills[random.nextInt(39)];
					if(skillsForPerson.contains(randomSkill) == false)
							skillsForPerson.add(randomSkill);
				}//end while
				for(int y = 0; y < skillsForPerson.size(); y++){
					System.out.println("\nINSERT INTO skill_set VALUES(" + personId[x] + ",\n\t"
							+ skillsForPerson.get(y) + ");");

					numberOfStatements++;
				}//inner for

			}//end outer for
			System.out.println("\n\nNumber of statements: " + numberOfStatements);
		}//end if 0


		else if(printInsert == 1){
			Scanner input = new Scanner(System.in);
			int numberOfStatements = 0;
			String skill_required;
			for(int x = 0; x < pos_code.length; x++){	
				ArrayList<String> skillsRequired = new ArrayList<String>();
				int numberSkills = 0;
				System.out.println("give skill required for: " + pos_code[x]);
					skill_required = input.next().toString();

				//while(!input.next().toString().equals("1")){
				while(skill_required.equals("1") == false){
					if(skillsRequired.contains(skill_required) == false)	{
						skillsRequired.add(skill_required);
						numberSkills++;
					}
					System.out.println("Give skill required for: " + pos_code[x] + " (" + numberSkills + " total)");
					skill_required = input.next().toString();

				}//end while
				for(int y = 0; y < skillsRequired.size(); y++){
					System.out.println("\nINSERT INTO skills_required VALUES(" + pos_code[x] + ",\n\t"
							+ skillsRequired.get(y) + ");");

					numberOfStatements++;

				}//end for
			System.out.println("\n\nNumber of statements: " + numberOfStatements);
			}//end for 	
		}//end if 1

		else if(printInsert == 2){
			Scanner input = new Scanner(System.in);
			int skills = 0;
			String skill_offered;
			for(int x = 0; x < course_codes.length; x++){
				ArrayList<String> skillsOffered = new ArrayList<String>();
				System.out.println("Give skill offered for " + course_codes[x] + " (" + skills+ " total)");
				skill_offered = input.next().toString();
				while(skill_offered.equals("1") == false){
					if(skillsOffered.contains(skill_offered) == false){
						skillsOffered.add(skill_offered);
						skills++;
					}//end if
					System.out.println("Give skill offered for " + course_codes[x] + " (" + skills+ " total)");
					skill_offered = input.next().toString();

				}//end while
				for(int y = 0; y < skillsOffered.size(); y++){
				System.out.println("\nINSERT INTO skills_taught VALUES(" + course_codes[x] + ",\n\t"
						+ skillsOffered.get(y) + ");");
				}//end inner for
			}//end for



		}//end if 2
	}//end main
}
