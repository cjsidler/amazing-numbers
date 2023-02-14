package numbers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        String[] properties = {
                "BUZZ",
                "DUCK",
                "PALINDROMIC",
                "GAPFUL",
                "SPY",
                "SQUARE",
                "SUNNY",
                "JUMPING",
                "HAPPY",
                "SAD",
                "EVEN",
                "ODD"
        };

        welcomeUsers();
        displayInstructions();

        while (true) {
            Scanner scanner = new Scanner(System.in);

            // Get a long from the user and verify that the user entered a long and that it is positive
            System.out.print("Enter a request: ");

            String requestLine = scanner.nextLine();
            String[] requestLineArray = requestLine.split(" ");

            // If user didn't enter anything, display the instructions
            if (requestLineArray.length == 0) {
                System.out.println();
                displayInstructions();
                continue;
            }

            // Verify the first number is a natural number or 0
            if (isNotLong(requestLineArray[0]) || Long.parseLong(requestLineArray[0]) < 0) {
                System.out.println();
                System.out.println("The first parameter should be a natural number or zero.");
                System.out.println();
                continue;
            }

            // If the user entered two numbers, verify the second number is natural
            if (requestLineArray.length == 2 && (isNotLong(requestLineArray[1]) || Long.parseLong(requestLineArray[1]) <= 0)) {
                System.out.println();
                System.out.println("The second parameter should be a natural number.");
                System.out.println();
                continue;
            }

            long num1 = Long.parseLong(requestLineArray[0]);

            System.out.println();

            switch (requestLineArray.length) {
                case 1 -> {
                    // 1 argument, display all properties of a single number
                    if (num1 == 0) {
                        System.out.println("Goodbye!");
                        return;
                    }
                    displayNumProperties(num1);
                }
                case 2 -> {
                    // 2 arguments, display list of numbers with their properties
                    long num2 = Long.parseLong(requestLineArray[1]);

                    for (long i = num1; i < num1 + num2; i++) {
                        System.out.printf("\t%d is %s\n", i, displayInlineProperties(i));
                    }
                    System.out.println();
                }
                default -> {
                    // 3 or more arguments, display the numbers with the specified properties
                    String[] requestedProperties = Arrays.copyOfRange(requestLineArray, 2, requestLineArray.length);

                    // Verify requested properties are all valid
                    StringBuilder wrongProperties = new StringBuilder();
                    int numWrongProperties = 0;
                    for (String prop : requestedProperties) {
                        // If the prop starts with -, make sure the rest of the prop is valid
                        if ((prop.charAt(0) == '-' && !contains(properties, prop.substring(1))) ||
                                (prop.charAt(0) != '-' && !contains(properties, prop))) {
                            wrongProperties.append(prop.toUpperCase());
                            wrongProperties.append(", ");
                            numWrongProperties++;
                        }
                    }
                    if (numWrongProperties > 0) {
                        wrongProperties.delete(wrongProperties.length() - 2, wrongProperties.length());
                    }
                    String[] wrongPropertiesArray = wrongProperties.toString().split(",");
                    if (numWrongProperties == 1) {
                        System.out.printf("The property [%s] is wrong.%n", wrongPropertiesArray[0]);
                        System.out.printf("Available properties: %s%n", Arrays.toString(properties));
                        System.out.println();
                        break;
                    } else if (numWrongProperties > 1) {
                        System.out.printf("The properties %s are wrong.%n", Arrays.toString(wrongPropertiesArray));
                        System.out.printf("Available properties: %s%n", Arrays.toString(properties));
                        System.out.println();
                        break;
                    }

                    // Verify direct opposites are not present in requestedProperties
                    String directOppositeProp = verifyNoDirectOpposites(requestedProperties, properties);
                    if (directOppositeProp.length() > 0) {
                        System.out.printf("The request contains mutually exclusive properties: [%s, -%s]%n", directOppositeProp, directOppositeProp);
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    }

                    // Even and Odd, Duck and Spy, Sunny and Square
                    if (contains(requestedProperties, "EVEN") && contains(requestedProperties, "ODD")) {
                        System.out.println("The request contains mutually exclusive properties: [ODD, EVEN]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    } else if (contains(requestedProperties, "DUCK") && contains(requestedProperties, "SPY")) {
                        System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    } else if (contains(requestedProperties, "SUNNY") && contains(requestedProperties, "SQUARE")) {
                        System.out.println("The request contains mutually exclusive properties: [SUNNY, SQUARE]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    } else if (contains(requestedProperties, "-EVEN") && contains(requestedProperties, "-ODD")) {
                        System.out.println("The request contains mutually exclusive properties: [-EVEN, -ODD]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    } else if (contains(requestedProperties, "-DUCK") && contains(requestedProperties, "-SPY")) {
                        System.out.println("The request contains mutually exclusive properties: [-DUCK, -SPY]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    } else if (contains(requestedProperties, "-SUNNY") && contains(requestedProperties, "-SQUARE")) {
                        System.out.println("The request contains mutually exclusive properties: [-SUNNY, -SQUARE]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    } else if (contains(requestedProperties, "HAPPY") && contains(requestedProperties, "SAD")) {
                        System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    } else if (contains(requestedProperties, "-HAPPY") && contains(requestedProperties, "-SAD")) {
                        System.out.println("The request contains mutually exclusive properties: [-HAPPY, -SAD]");
                        System.out.println("There are no numbers with these properties.");
                        System.out.println();
                        break;
                    }

                    long num2 = Long.parseLong(requestLineArray[1]);
                    long curNum = num1;
                    while (num2 > 0) {
                        boolean numHasProperty = displayInlineProperties(curNum, requestedProperties);
                        if (numHasProperty) {
                            num2--;
                        }
                        curNum++;
                    }
                    System.out.println();
                }
            }
        }
    }

    private static String verifyNoDirectOpposites(String[] requestedProperties, String[] properties) {
        for (String prop : properties) {
            if (contains(requestedProperties, prop) && contains(requestedProperties, "-" + prop)) {
                return prop;
            }
        }
        return "";
    }

    private static StringBuilder displayInlineProperties(long i) {
        boolean isEven = i % 2 == 0;
        boolean isBuzz = numIsBuzz(i);
        boolean isDuck = numIsDuck(i);
        boolean isPalindromic = numIsPalindromic(i);
        boolean isGapful = numIsGapful(i);
        boolean isSpy = numIsSpy(i);
        boolean isSquare = numIsSquare(i);
        boolean isSunny = numIsSunny(i);
        boolean isJumping = numIsJumping(i);
        boolean isHappy = numIsHappy(i);

        StringBuilder propertyLine = new StringBuilder();
        if (isBuzz) propertyLine.append("buzz, ");
        if (isDuck) propertyLine.append("duck, ");
        if (isPalindromic) propertyLine.append("palindromic, ");
        if (isGapful) propertyLine.append("gapful, ");
        if (isSpy) propertyLine.append("spy, ");
        if (isSquare) propertyLine.append("square, ");
        if (isSunny) propertyLine.append("sunny, ");
        if (isJumping) propertyLine.append("jumping, ");
        if (isHappy) {
            propertyLine.append("happy, ");
        } else {
            propertyLine.append("sad, ");
        }
        if (isEven) {
            propertyLine.append("even, ");
        } else {
            propertyLine.append("odd, ");
        }
        propertyLine.delete(propertyLine.length() - 2, propertyLine.length());

        return propertyLine;
    }

    private static boolean displayInlineProperties(long i, String... requestedProperties) {
        String[] inlineProperties = displayInlineProperties(i).toString().split(", ");
        for (String prop : requestedProperties) {
            if (prop.charAt(0) == '-') {
                String negatedProp = prop.substring(1);
                if (contains(inlineProperties, negatedProp)) {
                    return false;
                }
            } else if (!contains(inlineProperties, prop)) {
                return false;
            }
        }
        System.out.printf("\t%d is %s\n", i, String.join(", ", inlineProperties));
        return true;
    }

    private static boolean isNotLong(String strLong) {
        if (strLong == null) {
            return true;
        }
        try {
            Long.parseLong(strLong);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    private static void welcomeUsers() {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println();
    }

    private static void displayInstructions() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
        System.out.println();
    }

    private static void displayNumProperties(long num) {
        System.out.printf("Properties of %d%n", num);

        System.out.printf("\tbuzz: %b%n", numIsBuzz(num));
        System.out.printf("\tduck: %b%n", numIsDuck(num));
        System.out.printf("\tpalindromic: %b%n", numIsPalindromic(num));
        System.out.printf("\tgapful: %b%n", numIsGapful(num));
        System.out.printf("\tspy: %b%n", numIsSpy(num));
        System.out.printf("\tsquare: %b%n", numIsSquare(num));
        System.out.printf("\tsunny: %b%n", numIsSunny(num));
        System.out.printf("\tjumping: %b%n", numIsJumping(num));
        System.out.printf("\thappy: %b%n", numIsHappy(num));
        System.out.printf("\tsad: %b%n", !numIsHappy(num));
        boolean isEven = num % 2 == 0;
        System.out.printf("\teven: %b%n", isEven);
        System.out.printf("\todd: %b%n", !isEven);

        System.out.println();
    }

    private static boolean numIsSpy(long num) {
        String[] numDigits = String.valueOf(num).split("");
        long total = 0;
        long product = 1;

        for (String digit : numDigits) {
            long longDigit = Long.parseLong(digit);
            total += longDigit;
            product *= longDigit;
        }

        return total == product;
    }

    private static boolean numIsGapful(long num) {
        StringBuilder numAsStringBuilder = new StringBuilder(String.valueOf(num));
        StringBuilder firstAndLastDigit = new StringBuilder();

        if (numAsStringBuilder.length() < 3) {
            return false;
        }

        firstAndLastDigit.append(numAsStringBuilder.charAt(0));
        firstAndLastDigit.append(numAsStringBuilder.charAt(numAsStringBuilder.length() - 1));

        return num % Long.parseLong(firstAndLastDigit.toString()) == 0;
    }

    private static boolean numIsPalindromic(long num) {
        StringBuilder numAsStringBuilder = new StringBuilder(String.valueOf(num));
        numAsStringBuilder.reverse();
        return String.valueOf(num).equals(numAsStringBuilder.toString());
    }

    private static boolean numIsDuck(long num) {
        String numAsString = String.valueOf(num);

        // Iterate over digits of num, if any digit is 0, it is a duck number
        for (char digit : numAsString.toCharArray()) {
            if (digit == '0') {
                return true;
            }
        }
        return false;
    }

    private static boolean numIsBuzz(long num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    private static boolean numIsSunny(long num) {
        return Math.sqrt(num + 1) - Math.floor(Math.sqrt(num + 1)) == 0;
    }

    private static boolean numIsSquare(long num) {
        return Math.sqrt(num) - Math.floor(Math.sqrt(num)) == 0;
    }

    private static boolean numIsJumping(long num) {
        String[] numDigits = String.valueOf(num).split("");

        // Iterate over digits of num, if any digit is 0, it is a duck number
        for (int i = 1; i < numDigits.length; i++) {
            int num1 = Integer.parseInt(numDigits[i - 1]);
            int num2 = Integer.parseInt(numDigits[i]);
            if (Math.abs(num1 - num2) != 1) {
                return false;
            }
        }

        return true;
    }

    // [2, 0, 0, 0]
    private static boolean numIsHappy(long num) {
        Set<Long> cache = new HashSet<Long>();
        cache.add(num);
        long curNum = num;
        while (curNum != 1) {
            long sum = 0;
            String[] curNumDigits = Long.toString(curNum).split("");
            for (String digit : curNumDigits) {
                sum += Math.pow(Long.parseLong(digit), 2);
            }
            if (cache.contains(sum)) {
                return false;
            }
            cache.add(sum);
            curNum = sum;
        }
        return true;
    }

    private static boolean contains(String[] strArray, String target) {
        for (String str : strArray) {
            if (str.equalsIgnoreCase(target)) {
                return true;
            }
        }
        return false;
    }
}
