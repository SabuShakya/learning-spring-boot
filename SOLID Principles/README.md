# SOLID Principles #

These are 5 principles focused on dependency management. Poor dependency management leads to code that is fragile, brittle 
and hard to change. On the other hand, proper dependency management leads to code that is easy to maintain. The SOLID 
principle helps to write better code by avoiding tight coupling and helping to maintain a code that is more testable and 
easier to maintain.

1. [Single Responsibility Principle](#single-responsibility-principle)
2. [Open/Closed Principle](#openclosed-principle)
3. [Liskov Substitution Principle](#liskov-substitution-principle)
4. [Interface Segregation Principle](#interface-segregation-principle)
5. [Dependency Inversion Principle](#dependency-inversion-principle)

## Single Responsibility Principle ##
![Alt text](./SingleResponsibilityPrinciple.jpg?raw=true "Title")

Every class should have a Single Responsibility,one single purpose, and a single reason to change. The classes should 
be smaller and specific.
The below class may seem fine. But, it has two responsibilities: manipulating and printing text. This violates the Single
Responsibility principle.
```java
public class TextManipulator {
    private String text;

    public TextManipulator(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void appendText(String newText) {
        text = text.concat(newText);
    }
    
    public String findWordAndReplace(String word, String replacementWord) {
        if (text.contains(word)) {
            text = text.replace(word, replacementWord);
        }
        return text;
    }
    
    public String findWordAndDelete(String word) {
        if (text.contains(word)) {
            text = text.replace(word, "");
        }
        return text;
    }

    public void printText() {
        System.out.println(textManipulator.getText());
    }
}
```
So, we should remove the print responsibility to a different class,
```java
public class TextPrinter {
    TextManipulator textManipulator;

    public TextPrinter(TextManipulator textManipulator) {
        this.textManipulator = textManipulator;
    }

    public void printText() {
        System.out.println(textManipulator.getText());
    }

    public void printOutEachWordOfText() {
        System.out.println(Arrays.toString(textManipulator.getText().split(" ")));
    }

    public void printRangeOfCharacters(int startingIndex, int endIndex) {
        System.out.println(textManipulator.getText().substring(startingIndex, endIndex));
    }
}

public class TextManipulator {
    private String text;

    public TextManipulator(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void appendText(String newText) {
        text = text.concat(newText);
    }
    
    public String findWordAndReplace(String word, String replacementWord) {
        if (text.contains(word)) {
            text = text.replace(word, replacementWord);
        }
        return text;
    }
    
    public String findWordAndDelete(String word) {
        if (text.contains(word)) {
            text = text.replace(word, "");
        }
        return text;
    }
}
```
Ref: [Single Responsibility](https://www.baeldung.com/java-single-responsibility-principle)

## Open/Closed Principle ##

![Alt text](./OpenClosedPrinciple.jpg?raw=true "Title")

Classes should be open for extension and closed for modifications. We should be able to extend class behaviour 
without modifying it. Private variables should be used only when required and not be exposed without need.

Example: Let's build OCP compliant calculator.
```java
public interface CalculatorOperation {
    void perform();
}
```
We can then create Addition, Subtraction, Division class implementing the interface.

```java
public class Addition implements CalculatorOperation {
    private double left;
    private double right;
    private double result;

    // constructor, getters and setters

    @Override
    public void perform() {
        result = left + right;
    }
}
public class Division implements CalculatorOperation {
    private double left;
    private double right;
    private double result;

    // constructor, getters and setters
    @Override
    public void perform() {
        if (right != 0) {
            result = left / right;
        }
    }
}
```
And finally, our calculator class doesn't need to implement new logic as we introduce new operators.
```java
public class Calculator {

    public void calculate(CalculatorOperation operation) {
        if (operation == null) {
            throw new InvalidParameterException("Cannot perform operation");
        }
        operation.perform();
    }
}
```
Ref: [Open/Closed Principle in Java](https://www.baeldung.com/java-open-closed-principle)

## Liskov Substitution Principle ##

![Alt text](./LiskovSubtitutionPrinciple.jpg?raw=true "Title")

Objects of superclass shall be replaceable by objects of subclasses without breaking the application. That is 
it should pass the ‘Is a’ test. Violations will fail the ‘is a test’.


For Details: [Liskov Substitution Principle in Java](https://www.baeldung.com/java-liskov-substitution-principle)

## Interface Segregation Principle ##

![Alt text](./InterfaceSegregationPrinciple.jpg?raw=true "Title")

Make many interfaces that are client specific rather than one general purpose interface. Keep your components 
focused and minimize dependencies between them.

Let us write a Payment interface.
```java
public interface Payment {
    void initiatePayments();
    Object status();
    List<Object> getPayments();
    void intiateLoanSettlement();
    void initiateRePayment();
}
```
BankPaymentImplementation:
```java
public class BankPayment implements Payment {

    @Override
    public void initiatePayments() {
       // ...
    }

    @Override
    public Object status() {
        // ...
    }

    @Override
    public List<Object> getPayments() {
        // ...
    }

    @Override
    public void intiateLoanSettlement() {
        throw new UnsupportedOperationException("This is not a loan payment");
    }

    @Override
    public void initiateRePayment() {
        throw new UnsupportedOperationException("This is not a loan payment");
    }

}
```
Next, we write the LoanPayment implementation:
```java
public class LoanPayment implements Payment {

    @Override
    public void initiatePayments() {
        throw new UnsupportedOperationException("This is not a bank payment");
    }

    @Override
    public Object status() {
        // ...
    }

    @Override
    public List<Object> getPayments() {
        // ...
    }

    @Override
    public void intiateLoanSettlement() {
        // ...
    }

    @Override
    public void initiateRePayment() {
        // ...
    }
}
```
Here, we see the problem that, we have unwanted implementations  in both BankPayment and Loan Payment.
By applying the ISP, we can solve the above issue by creating two different interface for BankPayment and Loan Payment, 
which extends the Payment interface with common methods. 

```java
public interface Payment {
    Object status();
    List<Object> getPayments();
}

public interface Bank extends Payment {
    void initiatePayments();
}

public interface Loan extends Payment {
    void intiateLoanSettlement();
    void initiateRePayment();
}
```

Then our implementation classes will be:
```java
public class BankPayment implements Bank {

    @Override
    public void initiatePayments() {
        // ...
    }

    @Override
    public Object status() {
        // ...
    }

    @Override
    public List<Object> getPayments() {
        // ...
    }
}

public class LoanPayment implements Loan {

    @Override
    public void intiateLoanSettlement() {
        // ...
    }

    @Override
    public void initiateRePayment() {
        // ...
    }

    @Override
    public Object status() {
        // ...
    }

    @Override
    public List<Object> getPayments() {
        // ...
    }
}
```
Ref: [Interface Segregation Principle in Java](https://www.baeldung.com/java-interface-segregation)

## Dependency Inversion Principle ##

![Alt text](./DependencyInversionPrinciple.jpg?raw=true "Title")

High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstraction should
not depend on details but details should depend on abstraction.
Let's see an example:

```java
    public class BackendDeveloper {
        public void writeJava(){
            //METHOD BODY
        }
    }
    public class FrontendDeveloper {
        public void writeJavaScript(){
            //METHOD BODY
        }
    }

    public class Project {
        private BackendDeveloper backendDev = new BackendDeveloper();
        private FrontendDeveloper frontendDev = new FrontendDeveloper();
        public void implement() {
            backend.writeJava();
            frontend.writeJavascript();
        }
    }
```
Here we see that Project class is a high-level module that depends on lower level modules as FrontendDeveloper and
BackendDeveloper. This violates the dependency inversion principle. To follow the principle we create an interface,
Developer and introduce an abstraction.

```java
    import java.util.List;public interface Developer {
        void develop();
    }

    public class BackendDeveloper implements Developer{
        
        @Override
        public void develop(){
            writeJava();
        }       

        public void writeJava(){
            //METHOD BODY
        }
    }
    public class FrontendDeveloper implements Developer {
    
        @Override
        public void develop(){
            writeJavaScript();
        }
        public void writeJavaScript(){
            //METHOD BODY
        }
    }

    public class Project {
        private List<Developer> developerList;
        
        public Project(List<Developer> developers){
            this.developerList = developers;
        }   
        public void implement() {
            developerList.forEach(developer -> developer.develop());
        }
    }
```
The outcome is that the Project class does not depend on lower level modules, but rather abstractions. 
Also, low-level modules and their details depend on abstractions.
Ref: [SOLID Principles: Dependency Inversion Principle](https://dzone.com/articles/solid-principles-dependency-inversion-principle)

Reference: 
- [Spring Guru Video on SOLID](https://springframework.guru/)