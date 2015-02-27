/**
 * This class will allow the User to specify the filename before they can interact with the Application by entering 
 * various commands like Add, Delete, Display, Clear and Exit. For example, User can enter "add little brown fox"
 * to store the information into a text file. Before executing the User's command, the Application will check 
 * whether the specified file exists before creating a new file. Upon successful initialization which includes
 * checking for no arguments and incorrect file format, the Application will then proceed to save the changes 
 * to the file.
 * 
 * 
 * @author 
 */

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TextBuddy {

	// ArrayList to store the data entered by the User
	private static ArrayList<String> fileContents;
	// ArrayList to store the search data
	private static ArrayList<String> searchFoundContents;

	private static String fileName = "";

	// Commands available to the User
	enum COMMAND {
		ADD, DISPLAY, DELETE, CLEAR, SEARCH, SORT, EXIT, INVALID
	};

	private static final String MESSAGE_WELCOME = "\nWelcome to TextBuddy. \"%1$s\" is ready for use.";
	private static final String MESSAGE_PROMPT = "\ncommand: ";
	private static final String MESSAGE_ADD = "\nAdded to \"%1$s\": \"%2$s\"";
	private static final String MESSAGE_DELETE = "\nDeleted from \"%1$s\": \"%2$s\"";
	private static final String MESSAGE_CLEAR = "\nAll content deleted from \"%1$s\".";
	private static final String MESSAGE_FOUND = "\nSearch found in \"%1$s\" with search key: \"%2$s\"";
	private static final String MESSAGE_NOT_FOUND ="\nSearch not found in \"%1$s\" with search key: \"%2$s\"";
	private static final String MESSAGE_SORT_COMPLETE = "\nSort completed.";
	private static final String MESSAGE_EMPTY_LIST = "\n\"%1$s\" is empty.";
	private static final String MESSAGE_EMPTY_SEARCH_LIST = "\nSearch list is empty.";

	private static final String MESSAGE_ERROR_NO_ARGUMENTS = "\nERROR: No arguments detected.";
	private static final String MESSAGE_ERROR_INCORRECT_FILE_FORMAT = "\nERROR: Incorrect File Format.";
	private static final String MESSAGE_ERROR_FILE_NOT_LOADED = "\nERROR: File not loaded.";
	private static final String MESSAGE_ERROR_FILE_NOT_CREATED = "\nERROR: File not created.";
	private static final String MESSAGE_ERROR_INVALID_COMMAND = "\nERROR: Invalid command.";
	private static final String MESSAGE_ERROR_UNRECOGNISED_COMMAND = "\nERROR: Unrecognised command.";
	private static final String MESSAGE_ERROR_INVALID_OPTION = "\nERROR: Invalid option.";
	private static final String MESSAGE_ERROR_FILE_NOT_SAVED = "\nERROR: File not saved.";

	public static void main(String[] args) {

		// Method call to check for no arguments
		exitIfNoArguments(args);

		// Method call to check for incorrect file format
		exitIfIncorrectFileFormat(args);

		// Method call to load the file data
		loadFileData(args);

		// Method call to execute User input
		executeUserInput();
	}

	
	/**
	 * This method will check whether the User specifies any arguments. If there
	 * are no arguments, User will get error message
	 * "ERROR: No arguments detected."
	 * 
	 * @param args This is the filename argument.			
	 */
	private static void exitIfNoArguments(String[] args) {
		if (args.length == 0) {
			displayMessage(MESSAGE_ERROR_NO_ARGUMENTS);

			// Method call to exit from the Application
			exit();
		}
	}

	/**
	 * This method will check whether the User specifies the correct file
	 * format, for example ".txt". If it is incorrect, User will get error
	 * message "ERROR: Incorrect File Format."
	 * 
	 * @param args This is the filename argument.
	 */
	public static void exitIfIncorrectFileFormat(String[] args) {
		// To extract the file extension
		String fileFormat = args[0].substring(args[0].length() - 4,
				args[0].length());

		if (!fileFormat.equalsIgnoreCase(".txt")) {
			displayMessage(MESSAGE_ERROR_INCORRECT_FILE_FORMAT);

			// Method call to exit from the Application
			exit();
		}
	}

	/**
	 * This method will load the file data to the ArrayList. If the specified
	 * file does not exist, Application will create the new file for storing the
	 * information entered by the User. Upon successful initialization, the User
	 * will see the welcome message and start interacting with the Application.
	 * 
	 * @param args This is the filename argument.
	 */
	public static void loadFileData(String[] args) {
		fileName = new String(args[0]);
		File fileDocument = new File(fileName);

		if (fileDocument.exists()) {
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				// Method call to retrieve data from BufferedReader
				retrieveData(bufferedReader);

				fileReader.close();

				displayMessage(String.format(MESSAGE_WELCOME, fileName));
			} catch (IOException e) {
				displayMessage(String.format(MESSAGE_ERROR_FILE_NOT_LOADED,
						fileName));
			}
		} else {
			try {
				// If file specified does not exist, proceed to create file
				fileDocument.createNewFile();

				//loadFileData(fileName);
			} catch (IOException e) {
				displayMessage(MESSAGE_ERROR_FILE_NOT_CREATED);

				// Method call to exit from the Application
				exit();
			}
		}
	}

	/**
	 * This method will make use of BufferedReader to read in the data and store
	 * in the ArrayList.
	 * 
	 * @param bufferedReader This is the BufferedReader object.
	 */
	private static void retrieveData(BufferedReader bufferedReader) {
		fileContents = new ArrayList<String>();

		try {
			String data = bufferedReader.readLine();

			while (data != null) {
				fileContents.add(data);
				data = bufferedReader.readLine();
			}
		} catch (IOException e) {
			displayMessage(String.format(MESSAGE_ERROR_FILE_NOT_LOADED,
					fileName));
		}
	}

	/**
	 * This method is used to print out messages
	 * 
	 * @param message This is the message to be printed out.
	 */
	private static void displayMessage(String message) {
		System.out.print(message);
	}

	/**
	 * This method will execute the User's input and process the commands
	 * accordingly before it is saved to the file.
	 */
	private static void executeUserInput() {
		String userInput = "";

		Scanner sc = new Scanner(System.in);

		do {
			displayMessage(MESSAGE_PROMPT);

			// Method call to read User's input
			userInput = getInputData(sc);

			// Method call to process User's input
			processInputData(userInput);

			// Method call to save data to the file
			saveFileData();

		} while (userInput != null);
	}

	/**
	 * This method will read in the User's input
	 * 
	 * @param sc This is the Scanner object.
	 * 
	 * @return The input string.
	 */
	private static String getInputData(Scanner sc) {
		return sc.nextLine();
	}

	/**
	 * This method will process the User's input, extracting the command and
	 * data separately
	 * 
	 * @param userInput This is the User input.
	 */
	public static void processInputData(String userInput) {
		ArrayList<String> resultList;
		String userCommand = getUserCommand(userInput);
		String userInputData = getUserInputData(userInput);

		// To determine the command type
		COMMAND commandType = determineCommandType(userCommand);

		// Switch case statements to perform various tasks like Add, Delete,
		// Display, Clear and Exit
		switch (commandType) {
		case ADD:
			// Method call to add data to the file
			addData(userInputData);
			break;
		case DISPLAY:
			// Method call to display the data in the file
			displayData();
			break;
		case DELETE:
			// Method call to delete the specified data
			deleteData(userInputData);
			break;
		case CLEAR:
			// Method call to clear all data in the file
			clearData();
			break;
		case SEARCH:
			// Method call to search for data based on search key
			resultList = searchData(userInputData);
			break;
		case SORT:
			// Method call to sort data
			resultList = sortData();
			break;
		case EXIT:
			// Method call to exit from the Application
			exit();
			break;
		case INVALID:
			displayMessage(MESSAGE_ERROR_INVALID_COMMAND);
			break;
		default:
			displayMessage(MESSAGE_ERROR_UNRECOGNISED_COMMAND);
			break;
		}
	}

	/**
	 * This method will extract the command from User's input
	 * 
	 * @param userInput This is the User input.
	 * 
	 * @return The User's command.
	 */
	private static String getUserCommand(String userInput) {
		String userCommand = userInput;
		int spaceIndex = userInput.indexOf(" ");

		if (spaceIndex != -1) {
			userCommand = userInput.substring(0, spaceIndex);
		}
		return userCommand;
	}

	/**
	 * This method will extract the data from User's input
	 * 
	 * @param userInput This is the User input.
	 * 
	 * @return The User's data.
	 */
	private static String getUserInputData(String userInput) {
		String userInputData = "";
		int userInputLength = userInput.length();
		int spaceIndex = userInput.indexOf(" ");
		
		if (spaceIndex != -1) {
			userInputData = userInput.substring(spaceIndex + 1,
					userInputLength);
		} 
		return userInputData;
	}

	/**
	 * This method will determine which command to execute based on User's
	 * command
	 * 
	 * @param userCommand This is the User's command.
	 * 
	 * @return The respective command that matches the User's command.
	 */
	private static COMMAND determineCommandType(String userCommand) {

		if (userCommand.equalsIgnoreCase("add")) {
			return COMMAND.ADD;
		} else if (userCommand.equalsIgnoreCase("delete")) {
			return COMMAND.DELETE;
		} else if (userCommand.equalsIgnoreCase("display")) {
			return COMMAND.DISPLAY;
		} else if (userCommand.equalsIgnoreCase("clear")) {
			return COMMAND.CLEAR;
		} else if(userCommand.equalsIgnoreCase("search")) {
			return COMMAND.SEARCH;
		} else if (userCommand.equalsIgnoreCase("sort")) {
			return COMMAND.SORT;
		} else if (userCommand.equalsIgnoreCase("exit")) {
			return COMMAND.EXIT;
		} else {
			return COMMAND.INVALID;
		}
	}

	/**
	 * This method will add data to the ArrayList before it is saved to the file
	 * 
	 * @param userInputData This is the User input data (to be added).
	 */
	private static void addData(String userInputData) {
		if (userInputData != null) {
			fileContents.add(userInputData);
			displayMessage(String.format(MESSAGE_ADD, fileName, userInputData));
		}
		saveFileData();
	}

	/**
	 * This method will display all the data in the file
	 */
	private static void displayData() {
		int dataCounter = 1;
		if (fileContents.size() > 0) {
			for(String data : fileContents) {
				System.out.println("\n" + (dataCounter++) + ". " + data);
			}
		} else {
			displayMessage(String.format(MESSAGE_EMPTY_LIST, fileName));
		}
	}

	/**
	 * This method will delete data specified by the User
	 * 
	 * @param userInputData This is the User input data (item to be deleted).
	 */
	private static void deleteData(String userInputData) {
		int userData = Integer.parseInt(userInputData);
		// To check whether the list is empty
		if (fileContents.size() > 0) {
			// To check whether the delete item index is out of bound
			if (userData <= fileContents.size() && (userData - 1) >= 0) {
				String data = fileContents.get(userData - 1);
				fileContents.remove(userData - 1);
				displayMessage(String.format(MESSAGE_DELETE, fileName, data));
			}
		} else {
			displayMessage(MESSAGE_ERROR_INVALID_OPTION);
		}
		saveFileData();
	}

	/**
	 * This method will clear all the data in the file
	 */
	public static void clearData() {
		fileContents.clear();
		displayMessage(String.format(MESSAGE_CLEAR, fileName));
		saveFileData();
	}
	
	/**
	 * This method will search for the data that matches the search key
	 * 
	 * @param userInputData This is the User input data (search key).
	 * 
	 * @return The list of items found.
	 */
	public static ArrayList<String> searchData(String userInputData) {
		boolean foundFlag = false;
		searchFoundContents = new ArrayList<String>();
		
		if(fileContents.size() > 0) {
			for(String data : fileContents)	{
				
				if(compareData(data, userInputData) && !userInputData.equals("")) {
					foundFlag = true;			
					searchFoundContents.add(data);
				}
			}
			if(foundFlag) {
				displaySearchData();
				displayMessage(String.format(MESSAGE_FOUND, fileName, userInputData));
			}
			else {
				displayMessage(String.format(MESSAGE_NOT_FOUND, fileName, userInputData));
			}
		}
		else {
			displayMessage(String.format(MESSAGE_EMPTY_LIST, fileName));
		}
		return searchFoundContents;
	}
	
	/**
	 * This method will compare the data with the search key
	 * 
	 * @param data This is the data in the file.
	 * 
	 * @param searchKey This is the User input data (search key).
	 * 
	 * @return True if the comparison is matched.
	 */
	private static boolean compareData(String data, String searchKey) {
		boolean compareFlag = false;
		
		if(data.toLowerCase().contains(searchKey.toLowerCase())) {
			compareFlag = true;
		}
		return compareFlag;
	}
	
	/**
	 * This method will display the search data
	 */
	private static void displaySearchData() {
		int dataCounter = 1;
		if (searchFoundContents.size() > 0) {
			for(String data : searchFoundContents) {
				System.out.println("\n" + (dataCounter++) + ". " + data);
			}
		} else {
			displayMessage(String.format(MESSAGE_EMPTY_SEARCH_LIST));
		}
	}
	
	/**
	 * This method will sort the data
	 * 
	 * @return The list of items sorted.
	 */
	public static ArrayList<String> sortData() {
		if (fileContents.size() > 0) {
			Collections.sort(fileContents);
			displayMessage(String.format(MESSAGE_SORT_COMPLETE, fileName));
		}
		else {
			displayMessage(String.format(MESSAGE_EMPTY_LIST, fileName));
		}
		saveFileData();
		return fileContents;
	}
	
	/**
	 * This method will exit the Application
	 */
	private static void exit() {
		System.exit(0);
	}

	/**
	 * This method will save the interactions by the User to the file. For
	 * example, like adding data
	 */
	public static void saveFileData() {
		try {
			FileWriter fileWriter = new FileWriter(fileName);

			// Method call to save data to the file
			saveData(fileWriter);

			fileWriter.close();
		} catch (IOException e) {
			displayMessage(MESSAGE_ERROR_FILE_NOT_SAVED);
		}
	}

	/**
	 * This method will make use of FileWriter to save data to the file
	 * 
	 * @param fileWriter This is the FileWriter object.
	 */
	private static void saveData(FileWriter fileWriter) {
		try {
			for (int i = 0; i < fileContents.size(); i++) {
				fileWriter.write(fileContents.get(i) + "\n");
			}
		} catch (IOException e) {
			displayMessage(MESSAGE_ERROR_FILE_NOT_SAVED);
		}
	}
}