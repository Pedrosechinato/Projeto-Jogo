package interfaces;

import java.awt.BorderLayout;
import main.Main;
import mensagem.Mensagem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InterfaceCriarSala extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNomeSala;

	private int cria = 0;
	/**
	 * Create the frame.
	 */
	public InterfaceCriarSala() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 450,150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JLabel lblNomeSala = new JLabel("Nome da sala");
		lblNomeSala.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNomeSala, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		textNomeSala = new JTextField();
		panel.add(textNomeSala);
		textNomeSala.setColumns(10);
		
		JButton btnCriarSala = new JButton("Criar ");
		btnCriarSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					transmissor.writeObject(new Mensagem("CRI", textNomeSala.getText()));
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
						
					if(resposta.getCom().equals("SUC"))
					{
						JOptionPane.showMessageDialog(null, "Jogo Criado");
						
						Main.TELA_CRIAR_SALA.setVisible(false);
						Main.TELA_LOBBY.setVisible(true);
						textNomeSala.setText("");
					}
					else if (resposta.getCom().equals("ERR"))
					{
						JOptionPane.showMessageDialog(null, "Erro ao Criar Jogo, nome ja utilizado");
					}
						
					receptor.close();
					transmissor.close();
					conexao.close();
				} catch(Exception e1) {
					System.err.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
				}
			}
		});
		contentPane.add(btnCriarSala, BorderLayout.SOUTH);
	}

	public int getCria() {
		return cria;
	}

	public void setCria(int cria) {
		this.cria = cria;
	}

}
