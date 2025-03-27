import java.util.Scanner;
class tnpattendance {
    static final int total_noofclass = 50;
    //abhi total class final rakha ha no change 
    static String[] names = {"ABHISHEK KUMAR","ADITYA AGNIHOTRI","ANKESH PANDEY","ANKIT SING","ASHWINI KUMAR","DISA WANJARE","HIMANSHU KUMAR","KAJAL KUMARI","NIKHIL PARMAR","RAGHUNANDAN KUMAR"};
    //demo total student
    static String[] roll_ofstu = {
            "0191CS233D01","0191CS233D02","0191CS233D03","0191CS233D04","0191CS233D05","0191CS233D06","0191CS233D07","0191CS233D08","0191CS233D09","0191CS233D10"};
            //demo roll
    static String[] categories = {
        "Japanes", "Japanes", "Japanes", "Japanes","Japanes","Japanes","Japanes","Japanes","Japanes","Japanes"};
    static int[] attendance = new int[names.length];

    // login id pass predefine check
    static String username = "admin";
    static String password = "12345";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Login check
        System.out.println("===||WELCOME TO OUR SOFTWARE PLEASE LOGIN ||TECHNOCRATES INTITUTE TECHNOLOGY BHOPAL===");
        System.out.println("If you Dont Have Username and Password Please contact mail ashwini32027@gmai.com");
        System.out.println("Enter username: ");
        String inputUsername = sc.next();
        System.out.println("Enter password: ");
        String inputPassword = sc.next();

        if (!inputUsername.equals(username) || !inputPassword.equals(password)) {
            System.out.println("Invalid username or password. Exiting...");
            System.exit(0);
            //if wrong exit fast that why we use .exit
        }

        while (true) {
            System.out.println("\n===== TNP Attendance Management Software By Ashwini Kumar(batch 2023-26) =====");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance Report");
            System.out.println("3. View Attendance Percentage");
            System.out.println("4. Sort Student List by Name");
            System.out.println("5. Search Attendance for a Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    markAttendance(sc);
                    break;
                case 2:
                    viewAttendanceReport();
                    break;
                case 3:
                    viewAttendancePercentage();
                    break;
                case 4:
                    sortStudentListMakeReport();
                    break;
                case 5:
                    searchStudentAttendance(sc);
                    break;
                case 6:
                    System.out.println("Exiting the program. Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
                    System.exit(0);
                    break;
            }
        }
    }

    // user sabse pehel attendance mark karega fir attendance ++ hoajega
    static void markAttendance(Scanner sc) {
        System.out.println("\nMark Attendance for Today Class of Tnp:");
        for (int i = 0; i < names.length; i++) {
            String present;
            while(true)
            {
            System.out.print("In Today Class Does  :" + names[i] + " (Roll No:" + roll_ofstu[i] + ") present? (y/n): ");
            present = sc.next().toLowerCase().trim();
            if(present.equals("y") || present.equals("n")) break;
            System.out.println("Please enter a valid input again use only present? (y/n) so Enter Again ");
            }
            if (present.equalsIgnoreCase("y")) {
                attendance[i]++;
            }
        }
        //extra space 
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("_====="+"All attendance Mark Succesfully"+"=====");
    }

    // student ka attendance report dekhne par switch case yaha return karega
    static void viewAttendanceReport() {
        System.out.println("\n======== Attendance report of all Tnp Student =======");
        for (int i = 0; i < names.length; i++) {
            System.out.println("Record of Student " + names[i] + "(Roll No:" + roll_ofstu[i] + ") | Categories "
                    + categories[i] + " | Attendance: " + attendance[i] + "/" + total_noofclass);
        }
    }

    // attendance ka percentage nikalana
    static void viewAttendancePercentage() {
        System.out.println("\n===== Attendance Percentage =====");
        for (int i = 0; i < names.length; i++) {
            double percentage = (attendance[i] * 100.0) / total_noofclass;
            System.out
                    .println(names[i] + "(Roll No:" + roll_ofstu[i] + " | Attendance Percentage: " + percentage + "%");
        }
    }

    // kunal kuswaha bubble sort logic
    static void sortStudentListMakeReport() {
        // Bubble sort for sorting by name
        for (int i = 0; i < names.length - 1; i++) {
            for (int j = 0; j < names.length - i - 1; j++) {
                if (names[j].compareTo(names[j + 1]) > 0) {
                    // Swap names
                    String tempName = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempName;
                    // Swap roll numbers
                    String tempRoll = roll_ofstu[j];
                    roll_ofstu[j] = roll_ofstu[j + 1];
                    roll_ofstu[j + 1] = tempRoll;
                    // Swap categories
                    String tempCategory = categories[j];
                    categories[j] = categories[j + 1];
                    categories[j + 1] = tempCategory;
                    // Swap attendance counts
                    int tempAttendance = attendance[j];
                    attendance[j] = attendance[j + 1];
                    attendance[j + 1] = tempAttendance;
                }
            }
        }
        System.out.println("\nStudent list sorted by Name:");
        for (int i = 0; i < names.length; i++) {
           double ok= (attendance[i] * 100.0) / total_noofclass;
            System.out.println("| |"+ names[i] + " (Roll No: " + roll_ofstu[i] +" | |Present day"+attendance[i]+""+"|Percentage |"+ok);

        }
    }

    // search student via roll number kyuki roll unique ha
    static void searchStudentAttendance(Scanner sc) {

        System.out.print("\nEnter roll number to search: ");
        String searchRoll = sc.next().toUpperCase();
        for (int i = 0; i < names.length; i++) {
            if (roll_ofstu[i].equals(searchRoll)) {
                System.out.println("Student Found: " + names[i] + " (Roll No: " + roll_ofstu[i] + ")");
                System.out.println("Category: " + categories[i]);
                System.out.println("Attendance: " + attendance[i] + "/" + total_noofclass);
                double percentage = (attendance[i] * 100.0) / total_noofclass;
                System.out.println("Attendance Percentage: " + percentage + "%");
                return; // Directly exit function after finding the student
            }
        }

        // Agar loop complete ho gaya, fir toh student nahi mila toh
        System.out.println("No student found with roll number: " + searchRoll);

}
}


