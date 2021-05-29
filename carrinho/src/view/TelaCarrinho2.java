package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class TelaCarrinho2 extends JFrame {
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCarrinho2 frame = new TelaCarrinho2();
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
	public TelaCarrinho2() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCarrinho2.class.getResource("/icones/favicon2.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("Carrinho");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaCarrinho2.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(392, 218, 32, 32);
		contentPane.add(lblStatus);

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(10, 3, 46, 36);
		contentPane.add(lblCodigo);

		txtcodigo = new JTextField();
		txtcodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecioCarrinho();

			}
		});
		txtcodigo.setBounds(81, 11, 86, 20);
		contentPane.add(txtcodigo);
		txtcodigo.setColumns(10);

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(10, 50, 46, 14);
		contentPane.add(lblProduto);

		txtproduto = new JTextField();
		txtproduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtproduto.setBounds(81, 42, 237, 20);
		contentPane.add(txtproduto);
		txtproduto.setColumns(10);

		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(10, 94, 54, 14);
		contentPane.add(lblQuantidade);

		txtquantidade = new JTextField();
		txtquantidade.setBounds(81, 91, 54, 20);
		contentPane.add(txtquantidade);
		txtquantidade.setColumns(10);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(204, 94, 46, 14);
		contentPane.add(lblValor);

		txtvalor = new JTextField();
		txtvalor.setBounds(249, 92, 69, 17);
		contentPane.add(txtvalor);
		txtvalor.setColumns(10);

		btnSelect = new JButton("");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirProduto();
			}
		});
		btnSelect.setIcon(new ImageIcon(TelaCarrinho2.class.getResource("/icones/select.png")));
		btnSelect.setBounds(40, 149, 64, 64);
		contentPane.add(btnSelect);

		btnUpdate = new JButton("");
		btnUpdate.setIcon(new ImageIcon(TelaCarrinho2.class.getResource("/icones/update2.png")));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 alterarCarrinho();
			}
		});
		btnUpdate.setBounds(174, 149, 64, 64);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarCarrinho();
			}
		});
		btnDelete.setIcon(new ImageIcon(TelaCarrinho2.class.getResource("/icones/delete2.png")));
		btnDelete.setBounds(289, 149, 64, 64);
		contentPane.add(btnDelete);

		btnShop = new JButton("");
		btnShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecioCarrinho();
			}

		});

		btnShop.setIcon(new ImageIcon(TelaCarrinho2.class.getResource("/icones/shop.png")));
		btnShop.setBounds(177, 3, 69, 32);
		contentPane.add(btnShop);

	}

	// fim do contrutor
	DAO dao = new DAO();
	private JTextField txtcodigo;
	private JTextField txtproduto;
	private JTextField txtquantidade;
	private JTextField txtvalor;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnSelect;
	private JButton btnShop;

	private void status() {
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			System.out.println(con);
			// trocando o icone do banco (status da coxão
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * selecionar carrinho
	 */
	private void selecioCarrinho() {
		String shop = "select *from carrinho where codigo = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();

			PreparedStatement pst = con.prepareStatement(shop);

			pst.setString(1, txtcodigo.getText());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtcodigo.setText(rs.getString(1)); // 1 -Codigo
				txtproduto.setText(rs.getString(2)); // 2 -produto
				txtquantidade.setText(rs.getString(3)); // 3 -Quantidade
				txtvalor.setText(rs.getString(4)); // 4 -Valor
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnShop.setEnabled(false);

			} else {
				JOptionPane.showMessageDialog(null, "Produto inexistente");
				btnSelect.setEnabled(true);
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
		if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Produto do carrinho");
		} else if (txtquantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Quantidade do carrinho");
		} else if (txtproduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else if (txtquantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 15 caracteres");
		} else if (txtvalor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else {

			{
				String create = "insert into carrinho (produto,quantidade,valor) values (?,?,?)";

				try {
					Connection con = dao.conectar();
					// substituir os parametros(insert no banco de dados)
					PreparedStatement pst = con.prepareStatement(create);
					pst.setString(1, txtproduto.getText());
					pst.setString(2, txtquantidade.getText());
					pst.setString(3, txtvalor.getText());
					// executar a quary
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Carrinho adicionado con sucesso");
					con.close();

					limpar();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void alterarCarrinho() {
		// Validação dos campos
		if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Produto do carrinho");
		} else if (txtquantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o quantidade do carrinho");
		} else if (txtproduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else if (txtquantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 15 caracteres");
		} else if (txtvalor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else {

			{
				String update = "update carrinho set produto=?,quantidade=?,valor=? where codigo=?";

				try {
					Connection con = dao.conectar();
					// substituir os parametros(insert no banco de dados)
					PreparedStatement pst = con.prepareStatement(update);
					pst.setString(1, txtproduto.getText());
					pst.setString(2, txtquantidade.getText());
					pst.setString(3, txtvalor.getText());
					pst.setString(4, txtcodigo.getText());
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
	private void deletarCarrinho() {
		String delete = "delete from carrinho where codigo=?";
		// Confirmação de exclusão do contato
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão deste carrinho?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtcodigo.getText());
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
		txtcodigo.setText(null);
		txtproduto.setText(null);
		txtquantidade.setText(null);
		// txtValor;
		txtvalor.setText(null);
		btnSelect.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnShop.setEnabled(true);

	}
}
