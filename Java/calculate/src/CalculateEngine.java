import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Java 1 Lesson 8
 *
 * @author Mikhail Silantev
 * @version date 24.08.2017.
 */
public class CalculateEngine implements ActionListener {
    Calculate parent;
    double currentResult=0;
    char selectAction=' ';
    String tempText="";
    CalculateEngine(Calculate parent){
        this.parent=parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickButton=(JButton)e.getSource();
        String displayFieldText=parent.textField.getText();
        String displayButtonText=clickButton.getText();
        tempText+=displayButtonText;
        parent.textLabel.setText(tempText);
        double displayValue=0;
        if(!displayFieldText.equals("")){
            displayValue=Double.parseDouble(displayFieldText);
        }
        if(displayButtonText=="+"){
            selectActionKey('+',displayValue);
        } else if(displayButtonText=="-"){
            selectActionKey('-',displayValue);
        } else if(displayButtonText=="*"){
            selectActionKey('*',displayValue);
        } else if(displayButtonText=="/"){
            selectActionKey('/',displayValue);
        } else if (displayButtonText=="C"){
            tempText="";
            parent.textField.setText(tempText);
            parent.textLabel.setText(tempText);
            return;
        }
        if(displayButtonText.equals("=")){
            switch (selectAction){
                case '+': {
                    currentResult+=displayValue;
                    break;
                }
                case '-': {
                    currentResult-=displayValue;
                    break;
                }
                case '*': currentResult*=displayValue; break;
                case '/': {
                    if(displayValue==0) currentResult=0;
                    else currentResult/=displayValue;
                    break;
                }
                default:
                    currentResult=0;
            }

            parent.textField.setText(""+currentResult);
            tempText+=currentResult;
            parent.textLabel.setText(tempText);
        }
        if(!displayButtonText.equals("+") && !displayButtonText.equals("-") && !displayButtonText.equals("*")
                && !displayButtonText.equals("/") && !displayButtonText.equals("=")){
            parent.textField.setText(displayFieldText+displayButtonText);
        }
    }

    void selectActionKey(char value,double displayValue){
        selectAction=value;
        currentResult=displayValue;
        parent.textField.setText("");
    }
}
