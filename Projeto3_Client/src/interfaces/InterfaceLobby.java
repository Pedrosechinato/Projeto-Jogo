package interfaces;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import main.Main;
import mensagem.Mensagem;

import javax.swing.ButtonGroup;
//import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
//import javax.swing.JRadioButton;


public class InterfaceLobby extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	/**
	 * Create the frame.
	 */
	public InterfaceLobby() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JButton btnEntrar = new JButton("Entrar");
		panel.add(btnEntrar);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		ButtonGroup bg = new ButtonGroup();
		
		JButton btnCriarPartida = new JButton("Criar Novo Jogo");
		btnCriarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.TELA_CRIAR_SALA.setVisible(true);
				
				}
			}
		);
		
		contentPane.add(btnCriarPartida, BorderLayout.SOUTH);
		
		JLabel lblSalas = new JLabel("Salas");
		lblSalas.setFont(new Font("Sylfaen", Font.PLAIN, 17));
		lblSalas.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSalas, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);
		
		JButton btnAtualizar = new JButton("Atualizar");
		panel_2.add(btnAtualizar);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					transmissor.writeObject(new Mensagem("PAR"));
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
					if(resposta.getCom().equals("PAR"))
					{	
						panel_1.removeAll();
						String[] s = resposta.getNomeSala().split(Pattern.quote(","));
						for (int i = 0; i < s.length ;i++) {
							JRadioButton rdbtnNewRadioButton = new JRadioButton(s[i]);
							panel_1.add(rdbtnNewRadioButton);
							rdbtnNewRadioButton.setActionCommand(s[i]);
							bg.add(rdbtnNewRadioButton);
						}
						setVisible(false);
						setVisible(true);	
						
					}
					
					receptor.close();
					transmissor.close();
					conexao.close();
				} catch(Exception e1) {
					System.err.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Erro na Busca");
				}
			}
		});
		
		
		
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					
					
					Mensagem mensagem = new Mensagem("ENT");
					
					mensagem.setEmail(Main.email);
					mensagem.setNomeSala(bg.getSelection().getActionCommand());
					
					transmissor.writeObject(mensagem);
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
						
					if(resposta.getCom().equals("SUC"))
					{
						Main.nome_sala = resposta.getNomeSala();
						Main.moedas =resposta.getMoedas();
						JOptionPane.showMessageDialog(null, "Partida encontrada");
						JOptionPane.showMessageDialog(null, "Entrou com " + resposta.getMoedas() +" Moedas");
						Main.TELA_LOBBY.setVisible(false);
						Main.TELA_SALA.setVisible(true);
					}
					else if(resposta.getCom().equals("ERR"))
					{
						JOptionPane.showMessageDialog(null, "Erro ao Buscar Partida");
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
	}

}
