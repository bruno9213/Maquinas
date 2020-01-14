package threading;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * Classe JFrame_Admin que serve de interface para o painel do Administrador
 */
public class JFrame_Admin extends javax.swing.JFrame {

    private String[] id, nome, user, mail, type;

    private JFrame_Cliente parent;

    @Override
    public JFrame_Cliente getParent() {
        return parent;
    }

    /**
     * Método setParent define qual a frame anterior a esta.
     *
     * @param parent
     */
    public void setParent(JFrame_Cliente parent) {
        this.parent = parent;
    }

    /**
     * Cria um novo form JFrame_Admin que vai mostrar a lista das entidades
     * registadas na BD.
     */
    public JFrame_Admin() {
        initComponents();
    }

    /**
     * Método screenWidth retorna a largura do ecrã.
     *
     * @return
     */
    public int screenWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getWidth();
    }

    /**
     * Método screenHeight retorna a altura do ecrã.
     *
     * @return
     */
    public int screenHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getHeight();
    }

    /**
     * Método setLista recebe como parametros ArrayLists dos dados das entidades
     * e coloca-os na tabela
     *
     * @param id
     * @param nome
     * @param user
     * @param mail
     * @param type
     */
    public void setLista(ArrayList<String> id, ArrayList<String> nome, ArrayList<String> user, ArrayList<String> mail, ArrayList<String> type) {
        this.id = id.toArray(String[]::new);
        this.nome = nome.toArray(String[]::new);
        this.user = user.toArray(String[]::new);
        this.mail = mail.toArray(String[]::new);
        this.type = type.toArray(String[]::new);
        updateTable();
    }

    /**
     * Método updateTable faz o update da tabela com os dados recebidos.
     *
     */
    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        for (int i = 0; i < id.length; i++) {
            if (id[i] != null || id[i].equals("")) { //just in case
                String[] item = {id[i], nome[i], user[i], mail[i], type[i]};
                model.addRow(item);
            }
        }
        jTable2.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Painel de Administrador");

        jLabel2.setText("Lista de Entidades");

        jButton3.setText("Criar Entidade");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Eliminar Entidade");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setText("Voltar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "User", "Mail", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addComponent(jButton5)))
                        .addContainerGap())))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton4))
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addGap(40, 40, 40))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(77, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // voltar
        this.setVisible(false);
        this.dispose();
        this.parent.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Criar entidade
        JFrame_AdminCriar jac = new JFrame_AdminCriar();
        jac.setParent(this);
        this.setVisible(false);
        jac.setLocation((screenWidth() / 2) - (jac.getSize().width / 2), (screenHeight() / 2) - (jac.getSize().height / 2));
        jac.setLista(user, mail);
        jac.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            // eliminar entidade
            int i = jTable2.getSelectedRow();
            if (i == -1) {
                return;
            }
            //confirmação
            int option = JOptionPane.showConfirmDialog(null, "De certeza que quer eliminar?", "Aviso", JOptionPane.YES_NO_OPTION);
            if (option == 1) {
                return;
            }
            Object remove = jTable2.getValueAt(i, 2);
            String user = remove.toString();
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.removeRow(i);

            try (Socket conexao = new Socket("127.0.0.1", 8095)) {//conexao com servidor login
                //enviar comando eliminar
                DataOutputStream dout = new DataOutputStream(conexao.getOutputStream());
                dout.writeUTF("eliminar");
                dout.flush();

                //enviar dados entidade
                dout.writeUTF(user);
                dout.flush();
            }

            //terminar
            this.parent.dispose();
            this.setVisible(false);
            this.dispose();

            JOptionPane.showMessageDialog(null, "Entidade Eliminada com sucesso.");

            Socket conexao2 = new Socket("127.0.0.1", 8090);
            Thread t;
            JFrame_Cliente j = new JFrame_Cliente();
            j.loginAdmin();
            t = new Thread(new Client(conexao2, j));
            t.start();
            j.setVisible(true);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
