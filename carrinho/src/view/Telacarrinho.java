package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class Telacarrinho extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtCodigo;
	private JTextField txtProduto;
	private JTextField txtQuantidade;
	private JTextField txtValor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Telacarrinho frame = new Telacarrinho();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Telacarrinho() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// ativaçao do formulario (formulario carregado)
				// status da conexão
				status();
			}
		});
		setTitle("carrinho");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Telacarrinho.class.getResource("/icones/favicon22.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Telacarrinho.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(388, 217, 32, 32);
		contentPane.add(lblStatus);

		JLabel btnCodigo = new JLabel("Codigo");
		btnCodigo.setBounds(20, 11, 46, 14);
		contentPane.add(btnCodigo);

		JLabel btnProduto = new JLabel("Produto");
		btnProduto.setBounds(20, 50, 46, 14);
		contentPane.add(btnProduto);

		JLabel btnQuantidade = new JLabel("Quantidade");
		btnQuantidade.setBounds(20, 101, 64, 14);
		contentPane.add(btnQuantidade);

		JLabel btnValor = new JLabel("Valor");
		btnValor.setBounds(254, 101, 46, 14);
		contentPane.add(btnValor);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(76, 8, 77, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtProduto = new JTextField();
		txtProduto.setBounds(76, 47, 247, 20);
		contentPane.add(txtProduto);
		txtProduto.setColumns(10);

		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(94, 98, 94, 20);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);

		txtValor = new JTextField();
		txtValor.setBounds(343, 98, 77, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);

		btnShop = new JButton("shop");
		btnShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleciocarrinho();
			}
		});
		btnShop.setEnabled(false);
		btnShop.setToolTipText("Shop");
		btnShop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnShop.setBorder(null);
		btnShop.setIcon(new ImageIcon(Telacarrinho.class.getResource("/icones/shop.png")));
		btnShop.setBounds(343, 41, 65, 32);
		contentPane.add(btnShop);

		btnCreate = new JButton("");
		btnCreate.setEnabled(false);
		btnCreate.setToolTipText("Adicionar carrinho");
		btnCreate.setBorder(null);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			inserircarrrinho();
			
				
			}
		});
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setBackground(SystemColor.control);
		btnCreate.setIcon(new ImageIcon(Telacarrinho.class.getResource("/icones/select.png")));
		btnCreate.setBounds(59, 167, 64, 64);
		contentPane.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarcarrinho();
			}
		});
		btnUpdate.setToolTipText("Voltar  contatos");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setBorder(null);
		btnUpdate.setBackground(SystemColor.control);
		btnUpdate.setIcon(new ImageIcon(Telacarrinho.class.getResource("/icones/update.png")));
		btnUpdate.setBounds(164, 167, 64, 64);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarcarrinho();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setToolTipText("Excluir contatos");
		btnDelete.setIcon(new ImageIcon(Telacarrinho.class.getResource("/icones/delete.png")));
		btnDelete.setBorder(null);
		btnDelete.setBackground(SystemColor.control);
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setBounds(295, 167, 64, 64);
		contentPane.add(btnDelete);
	} // fim do construtor

	// Criação de um objeto para acessar o método da classe Dao


	private JButton btnShop;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;

	/**
	 * 
	 */
	private void status() {
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			System.out.println(con);
			// trocando o icone do banco (status da coxão
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnShop.setEnabled(true);

				;
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * selecionar contato
	 */
	private void seleciocarrinho() {
		String read = "select *from carrinho where nome = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			// Preparar a construção MySQL
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parametro (?) pelo nome do contato
			pst.setString(1, txtProduto.getText());
			// resultado (obter os dados do contato pesquisado)
			ResultSet rs = pst.executeQuery();
			// se existir um contato correspondente
			if (rs.next()) {
				txtCodigo.setText(rs.getString(1)); // 1 -Produto
				txtQuantidade.setText(rs.getString(3)); // 3 -Quantidade
				txtValor.setText(rs.getString(4)); // 4 -Valor
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnShop.setEnabled(false);

			} else {
				// JOptionPane.showMessageDialog(null, "Produto inexistente");
				btnCreate.setEnabled(true);
				btnShop.setEnabled(false);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Ingerir um novo Produto CRUD Read
	 */
	private void inserirProduto() {
		// Validação dos campos
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Produto");
		} else if (txtQuantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do Produto");
		} else if (txtProduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else if (txtQuantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 15 caracteres");
		} else if (txtValor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else {

			{
				String create = "insert into carrinho (produto,quantidade,valor) values (?,?,?)";

				try {
					Connection con = dao.conectar();
					// substituir os parametros(insert no banco de dados)
					PreparedStatement pst = con.prepareStatement(create);
					pst.setString(1, txtProduto.getText());
					pst.setString(2, txtQuantidade.getText());
					pst.setString(3, txtValor.getText());
					// executar a quary
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Carrinho adicionado");
					con.close();

					limpar();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
 
	/**
	 * Editar contato CRUD Update
	 */

	private void alterarcarrinho() {
		// Validação dos campos
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do carrinho");
		} else if (txtQuantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o produto do carrinho");
		} else if (txtProduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else if (txtQuantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 15 caracteres");
		} else if (txtValor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else {

			{
				String update = "update carrinho set produto=?,quantidade=?,valor=? where idcon=?";

				try {
					Connection con = dao.conectar();
					// substituir os parametros(insert no banco de dados)
					PreparedStatement pst = con.prepareStatement(update);
					pst.setString(1, txtProduto.getText());
					pst.setString(2, txtQuantidade.getText());
					pst.setString(3, txtValor.getText());
					pst.setString(4, txtCodigo.getText());
					// executar a quary
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "carrinho alterado com sucesso");
					con.close();

					limpar();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}

	}

	/**
	 * Excluir contato CRUD Delete
	 * 
	 */
	private void deletarcarrinho() {
		String delete = "delete from carrinho where idcon=?";
		// Confirmação de exclusão do contato
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão deste carrinho?", "Atenção!", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());
				pst.executeUpdate();
				limpar();
				JOptionPane.showMessageDialog(null, "carrinho excluido");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			limpar();
		}
	
	}

	/**
	 * Limpar campos e configurar os botoes
	 */
	private void limpar() {
		txtCodigo.setText(null);
		txtProduto.setText(null);
		txtQuantidade.setText(null);
		// txtValor;
		txtValor.setText(null);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnShop.setEnabled(true);

	}
}