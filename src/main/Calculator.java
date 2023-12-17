package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Calculator App
 * 
 * @author Paul Siebenaler
 * @Date 12/05/23
 * @version 1.0
 * 
 * @apiNote
 * This is a Calculator app I created to better use GridBagConstraints. This is a basic calculator that
 * does simple operands. This app uses Stack arithmetic to evaluate the expressions in the window.
 */
public class Calculator implements KeyListener{
	final Color SAGE_GREEN = new Color(138, 154, 91);
	final static Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);
	JFrame main;
	JPanel back;
	static JTextArea results;

	
	
	Calculator() {
		main = new JFrame("Calculator");
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagConstraints m_cons = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		main.setLayout(gbl);
		m_cons.weightx = 1;
		m_cons.weighty = 1;
		m_cons.insets = new Insets(3,3,3,3);
		m_cons.fill = GridBagConstraints.BOTH;
		
		results = new JTextArea();
		results.setEditable(false);
		results.setBackground(Color.LIGHT_GRAY);
		results.setFont(BUTTON_FONT);
		results.setWrapStyleWord(true);;
		m_cons.gridwidth = 6;
		m_cons.gridx = 0;
		m_cons.gridy = 0;
		gbl.setConstraints(results, m_cons);
		main.add(results);
		
		
		
		m_cons.gridwidth = 1;
		m_cons.gridy = 1;
		
		ArrayList<JButton> butts = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			JButton tmp = new JButton();
			tmp.setText(String.valueOf(i));
			tmp.setFont(BUTTON_FONT);
			tmp.addActionListener(e-> {
				results.append(tmp.getText());
				main.requestFocus();
			});
			tmp.setBackground(SAGE_GREEN);
			butts.add(tmp);
		}
		for (int i = 7; i <= 9; i++) {
			m_cons.gridx = i-7;
			gbl.setConstraints(butts.get(i), m_cons);
			main.add(butts.get(i));
		}
		m_cons.gridy = 2;
		for (int i = 4; i <= 6; i++) {
			m_cons.gridx = i-4;
			gbl.setConstraints(butts.get(i), m_cons);
			main.add(butts.get(i));
		}
		m_cons.gridy = 3;
		for (int i = 1; i <= 3; i++) {
			m_cons.gridx = i-1;
			gbl.setConstraints(butts.get(i), m_cons);
			main.add(butts.get(i));
		}
		
		JButton clear = new JButton();
		clear.setBackground(SAGE_GREEN);
		clear.setFont(BUTTON_FONT);
		clear.setText(" C ");
		clear.addActionListener(e-> {
			results.setText(null);
			main.requestFocus();
		});
		
		m_cons.gridx =3;
		m_cons.gridy = 1;
		m_cons.gridwidth = 2;
		gbl.setConstraints(clear, m_cons);
		main.add(clear);
		
		char[] operands = {'+','-','*','/','='};
		JButton zero = butts.get(0);
		butts = new ArrayList<JButton>();
		for (char c: operands) {
			JButton tmp = new JButton();
			tmp.setBackground(SAGE_GREEN);
			tmp.setText(String.valueOf(c));
			tmp.setFont(BUTTON_FONT);
			if (c != '=') {
				tmp.addActionListener(e-> results.append(" " + String.valueOf(c) + " "));
			} else {
				tmp.addActionListener(e-> evaluate());
			}
			tmp.addActionListener(e -> main.requestFocus());
			butts.add(tmp);
		}
		m_cons.gridwidth = 1;
		m_cons.gridy = 2;
		gbl.setConstraints(butts.get(0), m_cons);
		main.add(butts.get(0));
		
		m_cons.gridx = 4;
		gbl.setConstraints(butts.get(1), m_cons);
		main.add(butts.get(1));
		
		m_cons.gridy = 3;
		m_cons.gridx = 3;
		gbl.setConstraints(butts.get(2), m_cons);
		main.add(butts.get(2));
		
		m_cons.gridx = 4;
		gbl.setConstraints(butts.get(3), m_cons);
		main.add(butts.get(3));
		
		m_cons.gridx = 0;
		m_cons.gridy = 4;
		m_cons.gridwidth = 2;
		gbl.setConstraints(zero, m_cons);
		main.add(zero);
		
		JButton dec = new JButton();
		dec.setBackground(SAGE_GREEN);
		dec.setFont(BUTTON_FONT);
		dec.setText(".");
		dec.addActionListener(e-> {
			results.append(".");
			main.requestFocus();
		});
		
		m_cons.gridx = 2;
		m_cons.gridwidth = 1;
		gbl.setConstraints(dec, m_cons);
		main.add(dec);
		
		JButton equals = butts.get(butts.size()-1);
		m_cons.gridx = 3;
		m_cons.gridwidth = 2;
		gbl.setConstraints(equals, m_cons);
		main.add(equals);
		
		m_cons.insets = new Insets(3,3,3,3);
		
		main.setBackground(new Color(53, 94, 59));
		main.setBounds(new Rectangle(400,550));
		main.setLocationRelativeTo(null);
		main.addKeyListener(this);
		main.setVisible(true);		
	}	
	
	
	public void evaluate() throws RuntimeException {
		results.getText().trim();
		String[] sub = results.getText().split(" ");
        Stack<String> ops = new Stack<>();
        Stack<Double> nums = new Stack<>();

        for (String s : sub) {
            if (s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*")) {
                ops.push(s);
            } else {
                nums.push(Double.valueOf(s));
            }

            while (!ops.isEmpty() && nums.size() >= 2) {
                double num2 = nums.pop();
                double num1 = nums.pop();
                String op = ops.pop();

                switch (op) {
                    case "+":
                        nums.push(num1 + num2);
                        break;
                    case "-":
                        nums.push(num1 - num2);
                        break;
                    case "/":
                        nums.push(num1 / num2);
                        break;
                    case "*":
                        nums.push(num1 * num2);
                        break;
                }
            }
        }
        results.setText(nums.pop().toString());
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (results.getText() == null) {
				return;
			}
			evaluate();
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

} 
