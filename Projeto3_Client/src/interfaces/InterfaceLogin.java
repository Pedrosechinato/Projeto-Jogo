package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Main;
import mensagem.Mensagem;

public class InterfaceLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField txtSenha;

	/**
	 * Create the frame.
	 */
	public InterfaceLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Main.TELA_CADASTRO.setVisible(true);
			}
		});
		btnCadastrar.setBounds(159, 227, 130, 23);
		contentPane.add(btnCadastrar);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(10, 78, 414, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(txtEmail.getText());
				System.out.println(txtSenha.getPassword());
				
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					transmissor.writeObject(new Mensagem("LOG", txtEmail.getText(), new String(txtSenha.getPassword())));
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
					
					if(resposta.getCom().equals("SUC"))
					{
						JOptionPane.showMessageDialog(null, "Logou");
						String email =  txtEmail.getText();
						Main.email = email;
						Main.TELA_LOBBY.setVisible(true);
						Main.TELA_LOGIN.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Erro ao logar");
					}
						
					receptor.close();
					transmissor.close();
					conexao.close();
				} catch(Exception e) {
					System.err.println(e.getMessage());
					JOptionPane.showMessageDialog(null, "Excessão ao logar");
				}
			}
		});
		btnLogar.setBounds(294, 227, 130, 23);
		contentPane.add(btnLogar);
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setBounds(10, 53, 46, 14);
		contentPane.add(lblEmail);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 109, 46, 14);
		contentPane.add(lblSenha);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(10, 227, 89, 23);
		contentPane.add(btnCancelar);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(10, 131, 414, 20);
		contentPane.add(txtSenha);
	}
}
