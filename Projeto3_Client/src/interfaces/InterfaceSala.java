package interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import main.Main;
import mensagem.Mensagem;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InterfaceSala extends JFrame {

	private JPanel contentPane;
	private JTextField textMoedas;
	private int pontos;
	/**
	 * Create the frame.
	 */
	public InterfaceSala() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel(Main.nome_sala);
		panel_1.add(lblNewLabel);
		
		JButton btnApostar = new JButton("Apostar");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		JButton btnComprarCarta = new JButton("Comprar Carta");
		btnComprarCarta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					Mensagem mensagem = new Mensagem("COM");
					mensagem.setNomeSala(Main.nome_sala);
					mensagem.setEmail(Main.email);
					
					transmissor.writeObject(mensagem);
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
						
					if(resposta.getCom().equals("CAR"))
					{
						System.out.println("AQUI");
						String[] s = resposta.getCarta().split(Pattern.quote("-"));
						for (int i = 0; i < s.length ;i++) 
							{	
							JLabel lblNewLabel_3 = new JLabel("Carta:"+ s[i] +" "+ s[i+1] );
							i++;
							panel_1.add(lblNewLabel_3);
							}
						setVisible(false);
						setVisible(true);
						JOptionPane.showMessageDialog(null, "comprou uma Carta");		
						if(resposta.getPontos()>21) {
							JOptionPane.showMessageDialog(null, "Estourou");
							btnComprarCarta.setEnabled(false);
						}
										

					}
					else if (resposta.getCom().equals("ERR"))
					{
						JOptionPane.showMessageDialog(null, "Erro na hora de comprar cartas");
					}
						
					receptor.close();
					transmissor.close();
					conexao.close();
				} catch(Exception e1) {
					System.err.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Erro na hora de comprar cartas");
				}
			}
		});
		btnComprarCarta.setPreferredSize(new Dimension(120,160));
		panel.add(btnComprarCarta);
		
		JButton btnParar = new JButton("Parar");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					Mensagem mensagem = new Mensagem("EOC");
					mensagem.setNomeSala(Main.nome_sala);
					mensagem.setEmail(Main.email);
					
					transmissor.writeObject(mensagem);
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
						
					if(resposta.getCom().equals("WIN"))
					{
						JOptionPane.showMessageDialog(null, "Ganhou!!");	
						JOptionPane.showMessageDialog(null, "Salto atualizador: "+ resposta.getMoedas()+" Moedas");
						btnComprarCarta.setEnabled(false);
						btnParar.setEnabled(false);
						btnApostar.setEnabled(true);
						setVisible(false);
						setVisible(true);	

					}
					else if (resposta.getCom().equals("ERR"))
					{
						JOptionPane.showMessageDialog(null, "Perdeu");
						JOptionPane.showMessageDialog(null, "Salto atualizador: "+ resposta.getMoedas()+" Moedas");
						if (resposta.getMoedas()<=0) 
						{
							JOptionPane.showMessageDialog(null, "Sem moedas");
							Main.TELA_SALA.setVisible(false);
							Main.TELA_LOBBY.setVisible(true);
						}
						btnComprarCarta.setEnabled(false);
						btnParar.setEnabled(false);
						btnApostar.setEnabled(true);
						setVisible(false);
						setVisible(true);	
						
					}
						
					receptor.close();
					transmissor.close();
					conexao.close();
				} catch(Exception e1) {
					System.err.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Erro ao começar partida");
				}
			}
		});
		btnParar.setPreferredSize(new Dimension(100,160));
		btnParar.setLocation(10,10);
		panel.add(btnParar);
		btnParar.setLocation(10,10);
		btnParar.setEnabled(false);
		btnComprarCarta.setEnabled(false);
		JButton btnComecarPartida = new JButton("Come\u00E7ar Partida");
		btnComecarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					transmissor.writeObject(new Mensagem("INI", Main.nome_sala));
					
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
						
					if(resposta.getCom().equals("SUC"))
					{
						JOptionPane.showMessageDialog(null, "Jogo Começou!");						

					}
					else if (resposta.getCom().equals("ERR"))
					{
						JOptionPane.showMessageDialog(null, "O Jogo ja começou ou poucos jogadores na sala");
					}
						
					receptor.close();
					transmissor.close();
					conexao.close();
				} catch(Exception e1) {
					System.err.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Erro ao começar partida");
				}
			}
		});
		contentPane.add(btnComecarPartida, BorderLayout.SOUTH);
		
		
		
		btnApostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					
					
					Mensagem mensagem = new Mensagem("APO");
					mensagem.setEmail(Main.email);
					mensagem.setNomeSala(Main.nome_sala);
					mensagem.setMoedas(Float.parseFloat(textMoedas.getText()));
					
					transmissor.writeObject(mensagem);
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
						
					if(resposta.getCom().equals("SUC"))
					{
				
					String[] s = resposta.getCarta().split(Pattern.quote("-"));
					for (int i = 0; i < s.length ;i++) 
						{	
						JLabel lblNewLabel_3 = new JLabel("Carta:"+ s[i] +" "+ s[i+1] );
						i++;
						panel_1.add(lblNewLabel_3);
						}
					btnParar.setEnabled(true);
					btnComprarCarta.setEnabled(true);
					btnApostar.setEnabled(false);
					setVisible(false);
					setVisible(true);	
					}
					else if(resposta.getCom().equals("ERR"))
					{
						JOptionPane.showMessageDialog(null, "Saldo Insuficiente ou partida nao começou");
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
		panel_1.add(btnApostar);
		
		textMoedas = new JTextField();
		panel_1.add(textMoedas);
		textMoedas.setColumns(10);
		
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Socket conexao = new Socket(Main.IP, Main.PORTA);
					ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
					ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
					

					Mensagem mensagem = new Mensagem("SAI");
					mensagem.setEmail(Main.email);
					mensagem.setNomeSala(Main.nome_sala);
					
					transmissor.flush();
					Mensagem resposta;
					
					resposta = (Mensagem) receptor.readObject();
						
					if(resposta.getCom().equals("SAI"))
					{
						JOptionPane.showMessageDialog(null, "Saiu");	
						Main.TELA_SALA.setVisible(false);
						Main.TELA_LOBBY.setVisible(true);

					}
					else if (resposta.getCom().equals("ERR"))
					{
						JOptionPane.showMessageDialog(null, "Erro ao sair da sala");
					}
						
					receptor.close();
					transmissor.close();
					conexao.close();
				} catch(Exception e1) {
					System.err.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Erro ao começar partida");
				}
			}
		});
		panel_2.add(btnSair);
	}

}
