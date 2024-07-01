/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poe.pkg3;
import javax.swing.JOptionPane;

public class POE {

    // Main method to start the application
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban and Task Management System!");

        // Registration and Login Section
        registrationAndLogin();

        // EasyKanban Section
        easyKanban();

        // TaskManager Section
        taskManager();
    }

    // Registration and Login functionality
    private static void registrationAndLogin() {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        String firstname = JOptionPane.showInputDialog("Enter first name:");
        String lastname = JOptionPane.showInputDialog("Enter last name:");

        boolean isUsernameValid = checkUsername(username);
        boolean isPasswordValid = checkPassword(password);

        if (isUsernameValid && isPasswordValid) {
            JOptionPane.showMessageDialog(null, "Account created successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Account creation failed. Please check the input fields.");
        }
    }

    // Check if username contains an underscore and is no more than 5 characters in length
    private static boolean checkUsername(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted");
            return false;
        }
    }

    // Check password complexity rules
    private static boolean checkPassword(String password) {
        if (password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*\\d.*") &&
            password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Password does not meet complexity requirements");
            return false;
        }
    }

    // EasyKanban functionality
    private static void easyKanban() {
        int choice;
        do {
            choice = Integer.parseInt(JOptionPane.showInputDialog(
                    "EasyKanban Options:\n" +
                            "1) Add tasks\n" +
                            "2) Show report\n" +
                            "3) Quit"
            ));

            switch (choice) {
                case 1:
                    addTasks();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Showing report (coming soon)");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Exiting EasyKanban...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    // Add tasks to the EasyKanban system
    private static void addTasks() {
        int numTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks:"));
        int totalHours = 0;
        for (int i = 0; i < numTasks; i++) {
            Task task = new Task();
            String name = JOptionPane.showInputDialog("Enter Task Name:");
            String description = JOptionPane.showInputDialog("Enter Task Description:");
            String developer = JOptionPane.showInputDialog("Enter Developer Details:");
            int duration = Integer.parseInt(JOptionPane.showInputDialog("Enter Task Duration (in hours):"));
            String status = JOptionPane.showInputDialog("Enter Task Status (To Do/Done/Doing):");
            task.setTask(name, description, developer, duration, status);
            totalHours += duration;
            JOptionPane.showMessageDialog(null, task.printTaskDetails());
        }
        JOptionPane.showMessageDialog(null, "Total hours across all tasks: " + totalHours);
    }

    // Task class for managing individual tasks
    static class Task {
        private String name;
        private String description;
        private String developer;
        private int duration;
        private String status;
        private static int taskNumber = 0;

        public void setTask(String name, String description, String developer, int duration, String status) {
            this.name = name;
            this.description = description;
            this.developer = developer;
            this.duration = duration;
            this.status = status;
            taskNumber++;
        }

        public String printTaskDetails() {
            return "Task Status: " + status + "\n" +
                    "Developer Details: " + developer + "\n" +
                    "Task Number: " + taskNumber + "\n" +
                    "Task Name: " + name + "\n" +
                    "Task Description: " + description + "\n" +
                    "Task ID: " + createTaskID() + "\n" +
                    "Task Duration: " + duration + "hrs";
        }

        String createTaskID() {
            String taskID = name.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" +
                    developer.substring(Math.max(0, developer.length() - 3)).toUpperCase();
            return taskID;
        }
    }

    // TaskManager functionality for managing tasks
    private static void taskManager() {
        TaskManager taskManager = new TaskManager(10); // Initialize with a capacity of 10 tasks

        // Populate some sample tasks
        taskManager.populateTasks(0, "John Doe", "Task A", 5, "To Do");
        taskManager.populateTasks(1, "Jane Smith", "Task B", 8, "Doing");
        taskManager.populateTasks(2, "Michael Johnson", "Task C", 3, "Done");

        // Perform operations on TaskManager
        taskManager.displayTasksDone();
        taskManager.displayLongestTask();
        taskManager.searchTaskByName("Task B");
        taskManager.searchTasksByDeveloper("John Doe");
        taskManager.deleteTaskByName("Task A");
        taskManager.displayFullReport();
    }

    // TaskManager class for managing tasks in a list
    static class TaskManager {
        private String[] developers;
        private String[] taskNames;
        private int[] taskIDs;
        private int[] taskDuration;
        private String[] taskStatus;
        private int size;
        private int currentIndex;

        public TaskManager(int size) {
            this.size = size;
            developers = new String[size];
            taskNames = new String[size];
            taskIDs = new int[size];
            taskDuration = new int[size];
            taskStatus = new String[size];
            currentIndex = 0;
        }

        public void populateTasks(int index, String developer, String taskName, int duration, String status) {
            developers[index] = developer;
            taskNames[index] = taskName;
            taskIDs[index] = index + 1; // Task ID starts from 1
            taskDuration[index] = duration;
            taskStatus[index] = status;
            currentIndex++;
        }

        public void displayTasksDone() {
            System.out.println("Tasks with status 'Done':");
            for (int i = 0; i < currentIndex; i++) {
                if (taskStatus[i].equalsIgnoreCase("Done")) {
                    System.out.println("Developer: " + developers[i] +
                            ", Task Name: " + taskNames[i] +
                            ", Task Duration: " + taskDuration[i]);
                }
            }
        }

        public void displayLongestTask() {
            int longestIndex = 0;
            for (int i = 1; i < currentIndex; i++) {
                if (taskDuration[i] > taskDuration[longestIndex]) {
                    longestIndex = i;
                }
            }
            System.out.println("Task with the longest duration:");
            System.out.println("Developer: " + developers[longestIndex] +
                    ", Task Duration: " + taskDuration[longestIndex]);
        }

        public void searchTaskByName(String name) {
            boolean found = false;
            for (int i = 0; i < currentIndex; i++) {
                if (taskNames[i].equalsIgnoreCase(name)) {
                    System.out.println("Task Name: " + taskNames[i] +
                            ", Developer: " + developers[i] +
                            ", Task Status: " + taskStatus[i]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Task with name '" + name + "' not found.");
            }
        }

        public void searchTasksByDeveloper(String developer) {
            System.out.println("Tasks assigned to developer '" + developer + "':");
            boolean found = false;
            for (int i = 0; i < currentIndex; i++) {
                if (developers[i].equalsIgnoreCase(developer)) {
                    System.out.println("Task Name: " + taskNames[i] +
                            ", Task Status: " + taskStatus[i]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No tasks assigned to developer '" + developer + "'.");
            }
        }

        public void deleteTaskByName(String name) {
            boolean deleted = false;
            for (int i = 0; i < currentIndex; i++) {
                if (taskNames[i].equalsIgnoreCase(name)) {
                    // Shift elements to delete the task
                    for (int j = i; j < currentIndex - 1; j++) {
                        developers[j] = developers[j + 1];
                        taskNames[j] = taskNames[j + 1];
                        taskIDs[j] = taskIDs[j + 1];
                        taskDuration[j] = taskDuration[j + 1];
                        taskStatus[j] = taskStatus[j + 1];
                    }
                    // Clear the last element
                    developers[currentIndex - 1] = null;
                    taskNames[currentIndex - 1] = null;
                    taskIDs[currentIndex - 1] = 0;
                    taskDuration[currentIndex - 1] = 0;
                    taskStatus[currentIndex - 1] = null;
                    currentIndex--;
                    deleted = true;
                    break;
                }
            }
            if (deleted) {
                System.out.println("Task '" + name + "' successfully deleted.");
            } else {
                System.out.println("Task with name '" + name + "' not found for deletion.");
            }
        }

        public void displayFullReport() {
            System.out.println("Full Task Report:");
            for (int i = 0; i < currentIndex; i++) {
                System.out.println("Task ID: " + taskIDs[i] +
                        ", Developer: " + developers[i] +
                        ", Task Name: " + taskNames[i] +
                        ", Task Duration: " + taskDuration[i] +
                        ", Task Status: " + taskStatus[i]);
            }
        }
    }
}

