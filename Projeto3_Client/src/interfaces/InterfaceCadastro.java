package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Main;
import mensagem.Mensagem;

import javax.swing.JPasswordField;


public class InterfaceCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jframe;
	private JTextField txtNome;
	private JTextField txtEmail;
	private JPasswordField txtSenha;
	private JPasswordField txtConfirmacao;

	/**
	 * Create the frame.
	 */
	public InterfaceCadastro() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 201);
		jframe = new JPanel();
		jframe.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jframe);
		jframe.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 11, 82, 14);
		jframe.add(lblNome);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(10, 36, 82, 14);
		jframe.add(lblEmail);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(10, 61, 82, 14);
		jframe.add(lblSenha);
		
		JLabel lblConfirmao = new JLabel("Confirma\u00E7\u00E3o:");
		lblConfirmao.setBounds(10, 86, 82, 14);
		jframe.add(lblConfirmao);
		
		txtNome = new JTextField();
		txtNome.setBounds(112, 8, 312, 20);
		jframe.add(txtNome);
		txtNome.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(112, 33, 312, 20);
		jframe.add(txtEmail);
		txtEmail.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Main.TELA_LOGIN.setVisible(true);
			}
		});
		btnCancelar.setBounds(15, 126, 89, 23);
		jframe.add(btnCancelar);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(Arrays.equals(txtSenha.getPassword(), txtConfirmacao.getPassword())))
				{
					JOptionPane.showMessageDialog(null, "Senha e confirmação não coincidem");
				}
				else
				{
					try {
						Socket conexao = new Socket(Main.IP, Main.PORTA);
						ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
						ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
						
						transmissor.writeObject(new Mensagem("CAD", txtEmail.getText(), new String(txtSenha.getPassword()), txtNome.getText(), 1000));
						transmissor.flush();
						Mensagem resposta;
						
						resposta = (Mensagem) receptor.readObject();
							
						if(resposta.getCom().equals("SUC"))
						{
							JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
						}
							
						receptor.close();
						transmissor.close();
						conexao.close();
					} catch(Exception e) {
						System.err.println(e.getMessage());
						JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
					}
				}
			}
		});
		btnOk.setBounds(335, 126, 89, 23);
		jframe.add(btnOk);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(112, 58, 312, 20);
		jframe.add(txtSenha);
		
		txtConfirmacao = new JPasswordField();
		txtConfirmacao.setBounds(112, 84, 312, 20);
		jframe.add(txtConfirmacao);
	}
}
