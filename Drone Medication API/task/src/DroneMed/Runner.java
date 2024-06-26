//package DroneMed;
//
//import DroneMed.models.*;
//import DroneMed.services.DroneService;
//import DroneMed.services.MedicationService;
//import DroneMed.services.UserService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Scanner;
//import java.util.Stack;
//
//@Component
//public class Runner implements CommandLineRunner {
//
//    private final DroneService droneService;
//
//    private final MedicationService medicationService;
//
//    private final UserService userService;
//
//    private final String INVALID_RESPONSE = "Invalid response, please enter a valid response.";
//    private final String INVALID_DRONE_PARAM = "The parameters you entered are not valid drone parameters, please enter a valid drone parameter.";
//    private final String INVALID_MED_PARAM = "The parameters you entered are not valid medication parameters, please enter a valid medication parameter.";
//    private final String INVALID_USER_PARAM = "The parameters you entered are not valid userAccounts parameters, please enter a valid userAccounts parameter.";
//
//    public Runner(DroneService droneService, MedicationService medicationService, UserService userService) {
//        this.droneService = droneService;
//        this.medicationService = medicationService;
//        this.userService = userService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter instruction for drone medication API...");
//        int response = 0;
//        lastResponse = response;
//        currentAction = Action.DEFAULT;
//        currentMode = Mode.DEFAULT;
//        availableOptions = 4;
//        System.out.println(options[response]);
//
//        while (running) {
//
//            //We expect int response
//            if (currentMode == Mode.DEFAULT) {
//                try {
//                    response = scanner.nextInt();
//                    if(currentAction == Action.DEFAULT && (response < BASE_OPTION || response > availableOptions)) {
//                        System.out.println(INVALID_RESPONSE);
//                        response = lastResponse; //reset response to default;
//                    }
//                    else if(currentAction != Action.DEFAULT && (response < BASE_OPTION || response > availableOptions)) {
//                        System.out.println(INVALID_RESPONSE);
//                        response = lastResponse; //reset response to default;
//                    }
//                    else response = processResponse(response, "");
//                }
//                catch (Exception e) {
//                    System.out.println(INVALID_RESPONSE);
//                    scanner.nextLine(); //this is to consume the invalid response
//                }
//
//            }
//            else {
//                //We expect string response
//                String info = scanner.nextLine();
//
//                if(processOptionsB(info)) {
//                    response = processResponse(lastResponse, info);
//                }
//            }
//
//            if(currentMode == Mode.DEFAULT) {
//                System.out.println(options[response]);
//            }
//        }
//
//        scanner.close();
//    }
//
//    public enum Action {
//        DEFAULT,
//        DRONES,
//        MEDICATIONS,
//        USERACCOUNTS
//    }
//
//    public enum Mode {
//        DEFAULT,
//        DETAILS,
//        UPDATES,
//    }
//
//
//
//    String[] options = new String[]{
//            """
//            1. Drones
//            2. Medications
//            3. UserAccounts
//            4. Exit""",
//            """
//            1. View Drones
//            2. Create Drone
//            3. Update Drone
//            4. Delete Drone
//            5. Get Drone
//            6. Back""",
//            """
//            1. View Medications
//            2. Create Medication
//            3. Update Medication
//            4. Delete Medication
//            5. Get Medication
//            6. Back""",
//            """
//            1. View UserAccounts
//            2. Create UserAccount
//            3. Update UserAccount
//            4. Delete UserAccount
//            5. Get UserAccount
//            6. Back"""
//    };
//
//    Action currentAction;
//
//    Mode currentMode;
//
//    //Stack<Action> lastAction = new Stack();
//
//    //To keep response backstack
//    Stack<Integer> stack = new Stack();
//
//    //flag to exist app
//    boolean running = true;
//
//    //User Response
//    int lastResponse;
//
//    int availableOptions = 0;
//    final int BASE_OPTION = 1;
//
//    private Optional<Drone> buildDrone(String[] values) {
//
//        try {
//            Drone drone = new Drone(
//                    values[0].trim(),
//                    Integer.parseInt(values[1].trim()),
//                    Integer.parseInt(values[2].trim()),
//                    Integer.parseInt(values[3].trim()),
//                    getEnumByString(DroneState.class, values[4].trim()),
//                    getEnumByString(DroneModel.class, values[5].trim()),
//                    values[6].trim()
//            );
//
//            return Optional.of(drone);
//        } catch (Exception e) {
//            System.out.println(INVALID_DRONE_PARAM);
//        }
//
//        return Optional.ofNullable(null);
//    }
//
//    private Optional<Medication> buildMedication(String[] values) {
//
//        try {
//            Medication medication = new Medication(
//                    values[0].trim(),
//                    values[1].trim(),
//                    Integer.parseInt(values[2].trim()),
//                    values[3].trim()
//            );
////            Medication medication = Medication.builder()
////                    .code(values[0].trim())
////                    .name(values[1].trim())
////                    .weight(Integer.parseInt(values[2].trim()))
////                    .imageURL(values[3].trim())
////                    .build();
//
//            return Optional.of(medication);
//        } catch (Exception e) {
//            System.out.println(INVALID_MED_PARAM);
//        }
//
//        return Optional.empty();
//    }
//
//    private Optional<UserAccount> buildUserAccounts(String[] values) {
//
//        try {
//            UserAccount userAccount = new UserAccount(
//                    getEnumByString(Account.class, values[0].trim()),
//                    values[1].trim(),
//                    values[2].trim(),
//                    values[3].trim(),
//                    values[4].trim()
//            );
////            UserAccount userAccount = UserAccount.builder()
////                    .account(UserAccount.Account.valueOf(values[0].trim()))
////                    .name(values[1].trim())
////                    .phoneNumber(values[2].trim())
////                    .address(values[3].trim())
////                    .gpsCoordinate(values[4].trim())
////                    .build();
//
//            return Optional.of(userAccount);
//        } catch (Exception e) {
//            System.out.println(INVALID_USER_PARAM);
//        }
//
//        return Optional.empty();
//    }
//
//
//    private int processResponse(int res, String info) {
//
//        int value = res;
//        //lastResponse = res;
//
//        if (currentAction == Action.DEFAULT) {
//            if (value == 4) {
//                System.exit(0);
//            }
//            else if (value >= 1 && value <= 4) {
//                //Set Action
//                if (value == 0) currentAction = Action.DEFAULT;
//                else if (value == 1) currentAction = Action.DRONES;
//                else if (value == 2) currentAction = Action.MEDICATIONS;
//                else if (value == 3) currentAction = Action.USERACCOUNTS;
//
//                if (stack.size() < 1)  stack.push(0);
//                lastResponse = value;
//                availableOptions = 6;
//                return value;
//            }
//            else {
//                System.out.println(INVALID_RESPONSE);
//                return  lastResponse;
//            }
//        }
//        else if (currentAction == Action.DRONES && currentMode == Mode.DEFAULT) {
//            if (value == 6) {
//                currentAction = Action.DEFAULT;
//                availableOptions = 4;
//                return stack.pop();
//            }
//            else if (value >= 1 && value <= 5) {
//                if(stack.size() < 2) stack.push(1);
//                DroneProcessor(value, info);
//                lastResponse = value;
//                return value;
//            }
//            else {
//                System.out.println(INVALID_RESPONSE);
//                return  lastResponse;
//            }
//        }
//        else if(currentAction == Action.MEDICATIONS && currentMode == Mode.DEFAULT) {
//            if(value == 6) {
//                currentAction = Action.DEFAULT;
//                availableOptions = 4;
//                return stack.pop();
//            }
//            else if (value >= 1 && value <= 5) {
//                if(stack.size() < 2)  stack.push(2);
//                MedicationProcessor(value, info);
//                lastResponse = value;
//                return value;
//            }
//            else {
//                System.out.println(INVALID_RESPONSE);
//                return  lastResponse;
//            }
//        }
//        else if(currentAction == Action.USERACCOUNTS && currentMode == Mode.DEFAULT) {
//            if(value == 6) {
//                currentAction = Action.DEFAULT;
//                availableOptions = 4;
//                return stack.pop();
//            }
//            else if (value >= 1 && value <= 5) {
//                if(stack.size() < 2)  stack.push(3);
//                UserAccountProcessor(value, info);
//                lastResponse = value;
//                return value;
//            }
//            else {
//                System.out.println(INVALID_RESPONSE);
//                return  lastResponse;
//            }
//        }
//        else if(currentMode == Mode.DETAILS ) {
//            if(value == 1 || info.equals("1")) {
//                availableOptions = 6;
//                currentMode = Mode.DEFAULT;
//                lastResponse = stack.peek();
//                return stack.pop();
//            }
//            else if(value == 2) {
//                if(currentAction == Action.DRONES) {
//                    if(lastResponse == 2 || lastResponse == 3) {
//                        DroneProcessor(2, "");
//                    }
//                    else DroneProcessor(lastResponse, "");
//                }
//                else if(currentAction == Action.MEDICATIONS) {
//                    if(lastResponse == 2 || lastResponse == 3) {
//                        MedicationProcessor(2, "");
//                    }
//                    else MedicationProcessor(lastResponse, "");
//                }
//                else if(currentAction == Action.USERACCOUNTS) {
//                    if(lastResponse == 2 || lastResponse == 3) {
//                        UserAccountProcessor(2, "");
//                    }
//                    else UserAccountProcessor(lastResponse, "");
//                }
//            }
//            else if(checkValidInt(info) && info.equals("2")) {
//                if(currentAction == Action.DRONES) {
//                    DroneProcessor(lastResponse, info);
//                }
//                else if(currentAction == Action.MEDICATIONS) {
//                    MedicationProcessor(lastResponse, info);
//                }
//                else if(currentAction == Action.USERACCOUNTS) {
//                    UserAccountProcessor(lastResponse, info);
//                }
//            }
//            else {
//                System.out.println(INVALID_RESPONSE);
//                return  lastResponse;
//            }
//        }
//        else if(currentMode == Mode.UPDATES) {
//            //Check if back was clicked
//            if(checkValidInt(info) && Integer.parseInt(info) == 1) {
//                currentMode = Mode.DEFAULT;
//                availableOptions = 6;
//                lastResponse = stack.peek();
//                return stack.pop();
//            }
//
//            //String[] values = info.replaceAll("\\s+", "").split(",");
//            String[] values = info.trim().split(",");
//
//            availableOptions = 2;
//
//            //Create and update
//            if (value == 2 || value == 3) {
//                if(currentAction == Action.DRONES) {
//                    if (values.length == 0 || values.length != 7) {
//                        System.out.println(INVALID_DRONE_PARAM);
//                    }
//                    else if (value == 2) {
//                        Optional<Drone> drone = buildDrone(values);
//                        if (drone.isPresent()) {
//                            String response = droneService.createDrone(drone.get());
//                            System.out.println("Create Drones" + "\n" + "1. Back" + "\n" + "2. Create more Drones");
//                            System.out.println(response);
//                            currentMode = Mode.DETAILS;
//                        }
//                    }
//                    else if (value == 3) {
//                        Optional<Drone> drone = buildDrone(values);
//                        if (drone.isPresent()) {
//                            String response = droneService.updateDrone(drone.get());
//                            System.out.println("Updated Drone" + "\n" + "1. Back" + "\n" + "2. Update more Drones");
//                            System.out.println(response);
//                            currentMode = Mode.DETAILS;
//                        }
//                    }
//                }
//                else if(currentAction == Action.MEDICATIONS) {
//                    if (values.length == 0 || values.length != 4) {
//                        System.out.println(INVALID_MED_PARAM);
//                    }
//                    else if (value == 2) {
//                        Optional<Medication> medication = buildMedication(values);
//                        if (medication.isPresent()) {
//                            String response = medicationService.createMedication(medication.get());
//                            System.out.println("Create medications" + "\n" + "1. Back" + "\n" + "2. Create more medications");
//                            System.out.println(response);
//                            currentMode = Mode.DETAILS;
//                        }
//                    }
//                    else if (value == 3) {
//                        Optional<Medication> medication = buildMedication(values);
//                        if (medication.isPresent()) {
//                            String response = medicationService.updateMedication(medication.get());
//                            System.out.println("Updated medications" + "\n" + "1. Back" + "\n" + "2. Update more medications");
//                            System.out.println(response);
//                            currentMode = Mode.DETAILS;
//                        }
//                    }
//                }
//                else if(currentAction == Action.USERACCOUNTS) {
//                    if(values.length == 0 || values.length != 5) {
//                        System.out.println(INVALID_USER_PARAM);
//                    }
//                    else if( value == 2) {
//                        Optional<UserAccount> userAccount = buildUserAccounts(values);
//                        if (userAccount.isPresent()) {
//                            String response = userService.createUser(userAccount.get());
//                            System.out.println("Create userAccounts" + "\n" + "1. Back" + "\n" + "2. Create more userAccounts");
//                            System.out.println(response);
//                            currentMode = Mode.DETAILS;
//                        }
//                    }
//                    else if( value == 3) {
//                        Optional<UserAccount> userAccount = buildUserAccounts(values);
//                        if (userAccount.isPresent()) {
//                            String response = userService.updateUser(userAccount.get());
//                            System.out.println("Updated userAccounts" + "\n" + "1. Back" + "\n" + "2. Update more userAccounts");
//                            System.out.println(response);
//                            currentMode = Mode.DETAILS;
//                        }
//                    }
//                }
//            } else if (value == 4 || value == 5) {
//                if(currentAction == Action.DRONES) {
//                    if( value == 4) {
//
//                        String response = droneService.deleteDrone(values[0]);
//                        System.out.println("Delete Drones" + "\n" + "1. Back" + "\n" + "2. Delete more Drones");
//                        System.out.println(response);
//                        // if(!response.endsWith("not found.")) {
//                        currentMode = Mode.DETAILS;
//                        //}
//
//
//                    }
//                    else if (value == 5) {
//                        Optional<Drone> drone = Optional.ofNullable(droneService.getDrone(values[0]));
//                        System.out.println("Get Drone" + "\n" + "1. Back" + "\n" + "2. Get more Drones");
//                        if(drone.isPresent()) {
//                            System.out.println(drone.get().toString());
//                        }
//                        else System.out.println("Drone with serial number " + values[0] + " not found.");
//                        currentMode = Mode.DETAILS;
//                    }
//                }
//                else if(currentAction == Action.MEDICATIONS) {
//                    if( value == 4) {
//
//                        String response = medicationService.deleteMedication(values[0]);
//                        System.out.println("Delete medications" + "\n" + "1. Back" + "\n" + "2. Delete more medications");
//                        System.out.println(response);
//                        currentMode = Mode.DETAILS;
//
//                    }
//                    else if( value == 5) {
//                        Optional<Medication> medication = medicationService.getMedication(values[0]);
//                        System.out.println("Get medications" + "\n" + "1. Back" + "\n" + "2. Get more medications");
//                        if(medication.isPresent()) {
//                            System.out.println(medication.get().toString());
//                        }
//                        else System.out.println("Medication with code " + values[0] + " not found.");
//                        currentMode = Mode.DETAILS;
//                    }
//                }
//                else if(currentAction == Action.USERACCOUNTS) {
//                    if( value == 4) {
//
//                        String response =  userService.deleteUser(values[0]);
//                        System.out.println("Delete userAccounts" + "\n" + "1. Back" + "\n" + "2. Delete more userAccounts");
//                        System.out.println(response);
//                        currentMode = Mode.DETAILS;
//
//                    }
//                    else if (value == 5) {
//                        Optional<UserAccount> userAccount =  userService.getUser(values[0]);
//                        System.out.println("Get userAccounts" + "\n" + "1. Back" + "\n" + "2. Get more userAccounts");
//                        if(userAccount.isPresent()) {
//                            System.out.println(userAccount.get().toString());
//                        }
//                        else System.out.println("UserAccount with phone number " + values[0] + " not found.");
//                        currentMode = Mode.DETAILS;
//                    }
//                }
//            }
//
//            return lastResponse;
//        }
//
//        return  0;
//    }
//
//    private boolean checkValidInt(String s) {
//        try {
//            if(s.length() > 1) return false;
//            int number = Integer.parseInt(s);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    private void UserAccountProcessor(int option, String info) {
//        availableOptions = 1;
//        if(option == 1) {
//
//            System.out.println("View UserAccounts" + "\n" + "1. Back");
//            currentMode = Mode.DETAILS;
//            List<UserAccount> userAccounts = userService.getAllUsers();
//
//            if(userAccounts.isEmpty()) System.out.println("The UserAccounts list is empty.");
//            else {
//                System.out.println("UserAccounts list");
//                for (int i=0; i < userAccounts.size(); i++) {
//                    System.out.println(i + 1  + ". " + userAccounts.get(i).toString());
//                }
//            }
//        }
//        else if(option == 2) {
//            System.out.println("Create UserAccount" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//            System.out.println("Enter new UserAccount information...." + "\n" + "User accountType, name, phoneNumber, address, gps coordinates ");
//
//        }
//        else if(option == 3) {
//            System.out.println("Update UserAccount" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            List<UserAccount> userAccounts = userService.getAllUsers();
//
//            if(userAccounts.isEmpty()) System.out.println("The UserAccounts list is empty.");
//            else {
//                System.out.println("UserAccounts list");
//                for (int i=0; i < userAccounts.size(); i++) {
//                    System.out.println(i + 1  + ". " + userAccounts.get(i).toString());
//                }
//
//                System.out.println("Enter UserAccount ID to update...." + "\n" + "User accountType, name, phoneNumber, address, gps coordinates  ");
//            }
//        }
//        else if(option == 4) {
//            System.out.println("Delete UserAccount" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//            List<UserAccount> userAccounts = userService.getAllUsers();
//
//            if(userAccounts.isEmpty()) System.out.println("The UserAccounts list is empty.");
//            else {
//                System.out.println("UserAccounts list");
//                for (int i=0; i < userAccounts.size(); i++) {
//                    System.out.println(i + 1  + ". " + userAccounts.get(i).toString());
//                }
//
//                System.out.println("Enter UserAccount ID to Delete...");
//            }
//        }
//        else if(option == 5) {
//            System.out.println("Get UserAccount" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//            System.out.println("Enter the UserAccount ID that you want to retrieve.");
//        }
//    }
//
//    private void MedicationProcessor(int option, String info) {
//        availableOptions = 1;
//        if(option == 1) {
//            System.out.println("View medications" + "\n" + "1. Back");
//            currentMode = Mode.DETAILS;
//            List<Medication> medications = medicationService.getAllMedication();
//
//            if(medications.isEmpty()) System.out.println("The medication list is empty.");
//            else {
//                System.out.println("Medications list");
//                for (int i=0; i < medications.size(); i++) {
//                    System.out.println(i + 1  + ". " + medications.get(i).toString());
//                }
//            }
//        }
//        else if(option == 2) {
//            System.out.println("Create medications" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            System.out.println("Enter new medication information...." + "\n" + "medication code, name, weight, imageURL ");
//
//        }
//        else if(option == 3) {
//            System.out.println("Update medications" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            List<Medication> medications = medicationService.getAllMedication();
//
//            if(medications.isEmpty()) System.out.println("The medications list is empty.");
//            else {
//                System.out.println("Medications list");
//                for (int i=0; i < medications.size(); i++) {
//                    System.out.println(i + 1  + ". " + medications.get(i).toString());
//                }
//
//                System.out.println("Enter medication ID to update...." + "\n" + "medication code, name, weight, imageURL ");
//            }
//        }
//        else if(option == 4) {
//            System.out.println("Delete medications" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            List<Medication> medications = medicationService.getAllMedication();
//
//            if(medications.isEmpty()) System.out.println("The medications list is empty.");
//            else {
//                System.out.println("Medications list");
//                for (int i=0; i < medications.size(); i++) {
//                    System.out.println(i + 1  + ". " + medications.get(i).toString());
//                }
//
//                System.out.println("Enter medication ID to Delete...");
//            }
//        }
//        else if(option == 5) {
//            System.out.println("Get medications" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            System.out.println("Enter the medication ID that you want to retrieve.");
//        }
//    }
//
//    private void DroneProcessor(int option, String info) {
//        availableOptions = 1;
//        if (option == 1) {
//
//            System.out.println("View drones" + "\n" + "1. Back");
//            currentMode = Mode.DETAILS;
//            List<Drone> drones = droneService.getAllDrones();
//
//            if(drones.isEmpty()) System.out.println("The drone list is empty.");
//            else {
//                System.out.println("Drones list");
//                for (int i=0; i < drones.size(); i++) {
//                    System.out.println(i + 1  + ". " + drones.get(i).toString());
//                }
//            }
//        }
//        else if (option == 2) {
//            System.out.println("Create drones" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            System.out.println("Enter new drone information...." + "\n" + "Serial Number, Max Weight, CurrentWeight, Battery Capacity, Drone State, Drone Model, GPS Coordinates");
//
//        }
//        else if(option == 3) {
//            System.out.println("Update drones" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            List<Drone> drones = droneService.getAllDrones();
//
//            if(drones.isEmpty()) System.out.println("The drones list is empty.");
//            else {
//                System.out.println("Drones list");
//                for (int i=0; i < drones.size(); i++) {
//                    System.out.println(i + 1  + ". " + drones.get(i).toString());
//                }
//
//                System.out.println("Enter drone ID to update...." + "\n" + "Serial Number, Max Weight, CurrentWeight, Battery Capacity, Drone State, Drone Model, GPS Coordinates ");
//            }
//        }
//        else if(option == 4) {
//            System.out.println("Delete drones" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            List<Drone> drones = droneService.getAllDrones();
//
//            if(drones.isEmpty()) System.out.println("The drones list is empty.");
//            else {
//                System.out.println("Drones list");
//                for (int i=0; i < drones.size(); i++) {
//                    System.out.println(i + 1  + ". " + drones.get(i).toString());
//                }
//
//                System.out.println("Enter drone ID to Delete....");
//            }
//        }
//        else if(option == 5) {
//            System.out.println("Get drones" + "\n" + "1. Back");
//            currentMode = Mode.UPDATES;
//
//            System.out.println("Enter drone the ID that you want to retrieve.");
//        }
//    }
//
//
//    private boolean processOptionsB(String info) {
//
//        if(info.length() > 0 && currentMode == Mode.DETAILS && availableOptions == 1 && !info.equals("1")) {
//            System.out.println(INVALID_RESPONSE);
//            return false;
//        }
//        else if(info.length() > 0 && currentMode == Mode.DETAILS && availableOptions == 2 && !info.equals("1") && !info.equals("2")) {
//            System.out.println(INVALID_RESPONSE);
//            return false;
//        }
//        else if(info.length() > 0 && currentMode == Mode.UPDATES && info.length() == 1 && availableOptions == 2 && !info.equals("1") && !info.equals("2")) {
//            System.out.println(INVALID_RESPONSE);
//            return false;
//        }
//        else if(info.length() > 0 && currentMode == Mode.UPDATES && info.length() == 1 && availableOptions == 1 && !info.equals("1")) {
//            System.out.println(INVALID_RESPONSE);
//            return false;
//        }
//
//        return info.length() > 0;
//    }
//
//
//    // Method to get enum value by string input
//    private static <T extends Enum<T>> T getEnumByString(Class<T> enumType, String input) {
//        for (T enumValue : enumType.getEnumConstants()) {
//            if (enumValue.toString().equalsIgnoreCase(input.trim())) {
//                return enumValue;
//            }
//        }
//        // Handle case where input does not match any enum constant
//        throw new IllegalArgumentException("No enum constant found for input: " + input);
//    }
//
//}
