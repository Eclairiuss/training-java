package fr.ebiz.nurdiales.trainingjava.cli;

import controller.CLIController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    /**
     * main function for run the CLIController.
     * @param args Arguments of the program launch.
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-cli.xml");
        CLIController cli = (CLIController) context.getBean("cli");
        cli.mainCLI();
    }
}
