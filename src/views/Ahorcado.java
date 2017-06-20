/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;
// Clases importadas

import com.sun.xml.internal.org.jvnet.fastinfoset.RestrictedAlphabet;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Paulino Cruz Mejía (PLUS-Juampa 19/06/2017)
 */
public class Ahorcado extends javax.swing.JFrame {

    /**
     * Creates new form Ahorcado
     */
    private Map<String, String> listaPalabras = new HashMap<>();    // Creación de un map String para un manejo mejor del archivo a leer
    private Integer intentos = 1;   // Creación de variable tipo objeto entero para conocer los intento del usuario
    private Integer contador = 0;   // Creación de varisble tipo objeto entero para conocer el índice de un próximo arreglo a crear

    private StringBuilder palabra = new StringBuilder();    // Creación de una palabra oculta que se usará para representar los guiones de las palabras

    // Constructor default
    public Ahorcado() {

        initComponents();   // Iniciando componentes
        centrarForma();     // Método usado para centrar aplicación en el centro de la pantalla

        txtPalabra.setEditable(false);  // No permitirá que la ventana de texto sea editable por el usuario
        txtDescripcion.setEditable(false);  // No permitirá que la ventana de texto sea editable por el usuario
        txtAdivinar.setEditable(false); // No permitirá que la ventana de texto sea editable por el usuario
        txtLetrasErradas.setEditable(false);    // No permitirá que la ventana de texto sea editable por el usuario
        btn_Detener.setEnabled(false);  // No permitirá al usuario interactuar con el botón

        // En caso de no poder ser leido el archivo es porque la ubicación de este debe ser de acuerdo de dónde se esté tomando la carpeta
        File archivo = new File("/home/juampa/Escritorio/Game-AhorcadoFin/Recursos_Ahorcado/archivo.txt");    // Marca el lugar de donde se leerá el archivo de texto
        leerArchivo(archivo);   // Métoodo para leer el archivo que lleva por parámetro la hubicación del mismo

        txtDescripcion.setText(""); // Pondrá en blanco la ventana de texto
        txtPalabra.setText("");     // Pondrá en blanco la ventana de texto

        lbl_intentos.setText("Intentos: 0");    // En el label colocará el texto indicando que está en el intento 0

    }

    // Método para leer el archivo de texto donde se encuentran almacenadas las palabras y descripciones a usar
    private void leerArchivo(File archivo) {

        int conta = 0;  // Variable de tipo entero 

        try {

            BufferedReader input = new BufferedReader(new FileReader(archivo));
            try {

                String line = null;
                while ((line = input.readLine()) != null) {
                    listaPalabras.put("" + conta, line);
                    conta++;

                }

            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }   // Fin de método para leer archivo de texto

    public void loadNextWord(int indice) {  // Método para leer las palabras en el archivo de texto

        String line = listaPalabras.get("" + indice);   // variable que leerá las palabras en el archivo de texto   
        StringBuilder wordConGuion = new StringBuilder();   // Creación de variable que colocará los guines de las palabras dependiendo el tamaño

        String[] datos = line.split(":");   // arreglo para colocar las palabras y descripciones de las misma, separadas por dos puntos como referencia

        for (Character car : datos[0].toUpperCase().toCharArray()) {    //Dependiendo de las letras de cada palabra, serán convertidas a mayúscula y a carácteres
            palabra.append(car);
            palabra.append(" ");
            wordConGuion.append("_");   // Mostrará un guón en lugar de un carácter
            wordConGuion.append(" ");   // Para separar los guines

        }

        txtDescripcion.setText(datos[1]);   // Tomará la descripción y la colocará en la caja de texto
        txtPalabra.setText(wordConGuion.toString());    // Colocará los guines dependiendo cuántos carácteres contenga la palabra
        txtAdivinar.setText("");    // Colocará el limpio el cuadro de texto

    }

    private void centrarForma() {   // Método para mostra la aplicación en el centro de la pantalla
        // Toma las dimensiones de la pantalla completa y se divide a la mitad para poder centrar la aplicación
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = this.getSize();
        double posX = (pantalla.getWidth() - ventana.getWidth()) / 2.0;
        double posY = (pantalla.getHeight() - ventana.getHeight()) / 2.0;
        this.setLocation((int) posX, (int) posY);

    }

    public void cambiarIntento() {  //  Método para cambiar imagen del ahorcado en caso de que una letra sea equivoca

        switch (intentos) { //  Dependiendo el número de intentos se colocará la imágen correspondiente y el número de intentos que lleve el usuario serán mostrados en el cuadro de texto correspondiente
            case 2:
                lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado02.png")));
                lbl_intentos.setText("Intentos: 2");
                break;
            case 3:
                lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado03.png")));
                lbl_intentos.setText("Intentos: 3");
                break;
            case 4:
                lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado04.png")));
                lbl_intentos.setText("Intentos: 4");
                break;
            case 5:
                lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado05.png")));
                lbl_intentos.setText("Intentos: 5");
                break;
            case 6:
                lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado06.png")));
                lbl_intentos.setText("Intentos: 6");
                break;
            case 7:
                lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado07.png")));
                lbl_intentos.setText("Intentos: 7");

                txtAdivinar.setText("");
                String mapString[] = listaPalabras.get("" + contador).split(":");

                if (contador == 9) { // En caso de que sea la última palabra y se haya equivocado, mostrará el siguiente menú
                    Object resp = JOptionPane.showInputDialog(null, "¿QUÉ DESEA HACER?", "                  "
                            + "** Perdió Completamente ** ", JOptionPane.QUESTION_MESSAGE, null,
                            new Object[]{"Reiniciar", "Salir"}, "Seleccione");

                    if (resp.equals("Reiniciar")) {   // Terminará e iniciará un juego nuevo
                        this.setVisible(false); // Borrará los datos y quitará la ventana del juego
                        Ahorcado.main(null);
                        return;
                    }
                    if (resp.equals("Salir")) {
                        System.exit(0); // Fin de ejecución del programa
                    }
                }
                JOptionPane.showMessageDialog(null, "No Acertó la Palabra");    // En caso de no ser asertada la palabra se mandará la ventanada notificando al usuario
                pasarPalabra(); // método para pasar la palabra en caso de no ser adivinada por el usuario y haber alcanzado el máximo de intentos petmitidos
                break;
            default:

        }
    }

    private void nextMenu() {   // Método que contiene el menú en caso de que la palabra haya sido acertada por el usuario mostrando diversas opciones
        Object resp = JOptionPane.showInputDialog(null, "¿QUÉ DESEA HACER?", "                       "
                + "** Acertó la Palabra ** ", JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Pasar a siguiente palabra", "Salir"}, "Seleccione");

        if (resp.equals("Pasar a siguiente palabra")) {   // Terminará e iniciará un juego nuevo
            txtAdivinar.setText("");
            pasarPalabra(); // Cambia a la suigiente palabra
            txtAdivinar.setText("");
        }
        if (resp.equals("Salir")) {
            System.exit(0); // Fin de ejecución del programa
        }
    }

    private void nextMenuDos() {   // Método que contiene el menú en caso de que haya un ganador al final de la última palabra
        Object resp = JOptionPane.showInputDialog(null, "¿QUÉ DESEA HACER?", "                            "
                + "** USTED GANÓ EL JUEGO** ", JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Reiniciar", "Salir"}, "Seleccione");

        if (resp.equals("Reiniciar")) {   // Terminará e iniciará un juego nuevo
            this.setVisible(false); // Borrará los datos y quitará la ventana del juego
            Ahorcado.main(null);
        }
        if (resp.equals("Salir")) {
            System.exit(0); // Fin de ejecución del programa
        }
    }

    private void pasarPalabra() {   // método para pasar la palabra en caso de ser erronea o acertada

        palabra = new StringBuilder();  // Eliminará lo que se tenga almacenado en la variable palabra para que pueda volver a usarse y hacer la comparación de carácteres
        txtAdivinar.setText("");    // Limpiará el cuadro de texto
        lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado01.png")));   // Colocará la primer imagen donde no se muestra ninguna parte del ahorcado
        lbl_intentos.setText("Intentos: 1");    // mostrará el inicio de intentos nuevamente
        intentos = 1;   // Colocará la variable en 1 para que se inicie un juego nuevo con la siguiente palabra

        contador++; // Al índice de contenedor de la palabras del archivo de texto se le hace un incremento para que pueda leer la próxima palabra

        loadNextWord(contador); // Método para leer la siguiente palabra en el archivo de texto
        txtLetrasErradas.setText("");   // Pondrá en blanco el cuadro de texto
        txtAdivinar.setText("");    // Pondrá en blanco el cuadro de texto
    }

    private void cambiaPalabras() { // Método para cambiar la palabra en caso de ser acertada
        txtAdivinar.setText("");    // Pondrá en blanco el cuadro de texto donde escribe el usuario
        String mapString[] = listaPalabras.get("" + contador).split(":");   // Creación de un arreglo para almacenar las palabras que se encuentran en el archivo de texto inicando que están referenciadas por los dos puntos ":"

        if (contador == 9 && mapString[0].equals(txtPalabra.getText().replace(" ", ""))) {    // En caso de ser la última palabramostrará el siguiente menú

            Object resp = JOptionPane.showInputDialog(null, "¿QUÉ DESEA HACER?", "** Ha Ganado Y Terminado El Juego ** ", JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Reiniciar", "Salir"}, "Seleccione");

            if (resp.equals("Reiniciar")) {   // Terminará e iniciará un juego nuevo
                this.setVisible(false); // Borrará los datos y quitará la ventana del juego
                Ahorcado.main(null);
                return;
            }
            if (resp.equals("Salir")) {
                System.exit(0); // Fin de ejecución del programa
            }

        }

        if (mapString[0].equals(txtPalabra.getText().replace(" ", ""))) {   // En caso de ser acertada la palabra por las teclas oprimidas por el usuario
            nextMenu(); // Mostrará el menú
            txtAdivinar.setText("");    // Pondrá en blanco el cuadro de texto
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbl_Imagen_Ahorcado = new javax.swing.JLabel();
        lbl_intentos = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txt_Titulo = new javax.swing.JLabel();
        lbl_Frase = new javax.swing.JLabel();
        btn_Iniciar = new javax.swing.JButton();
        btn_Detener = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lBl_Palabra_Oculta = new javax.swing.JLabel();
        txtPalabra = new javax.swing.JTextField();
        lbl_Pista = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        lbl_Adivinar = new javax.swing.JLabel();
        txtAdivinar = new javax.swing.JTextField();
        lbl_Palabras_Erradas = new javax.swing.JLabel();
        txtLetrasErradas = new javax.swing.JTextField();

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        lbl_Imagen_Ahorcado.setBackground(new java.awt.Color(117, 117, 117));
        lbl_Imagen_Ahorcado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ahorcado01.png"))); // NOI18N
        lbl_Imagen_Ahorcado.setOpaque(true);
        jPanel1.add(lbl_Imagen_Ahorcado, java.awt.BorderLayout.LINE_START);

        lbl_intentos.setBackground(new java.awt.Color(134, 134, 134));
        lbl_intentos.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        lbl_intentos.setForeground(new java.awt.Color(254, 254, 254));
        lbl_intentos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_intentos.setText("Intentos: 1");
        lbl_intentos.setOpaque(true);
        jPanel1.add(lbl_intentos, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_END);

        jPanel6.setLayout(new java.awt.BorderLayout());

        txt_Titulo.setBackground(new java.awt.Color(87, 87, 87));
        txt_Titulo.setFont(new java.awt.Font("Noto Mono", 1, 30)); // NOI18N
        txt_Titulo.setForeground(new java.awt.Color(254, 254, 254));
        txt_Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_Titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image.png"))); // NOI18N
        txt_Titulo.setText("Game \"Ahorcado\"");
        txt_Titulo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_Titulo.setMaximumSize(new java.awt.Dimension(800, 500));
        txt_Titulo.setOpaque(true);
        jPanel6.add(txt_Titulo, java.awt.BorderLayout.PAGE_START);

        lbl_Frase.setBackground(new java.awt.Color(117, 117, 117));
        lbl_Frase.setFont(new java.awt.Font("Noto Mono", 1, 24)); // NOI18N
        lbl_Frase.setForeground(new java.awt.Color(254, 254, 254));
        lbl_Frase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Frase.setText("\"¿Listo para ser Colgado?\"");
        lbl_Frase.setOpaque(true);
        jPanel6.add(lbl_Frase, java.awt.BorderLayout.CENTER);

        btn_Iniciar.setBackground(new java.awt.Color(150, 150, 150));
        btn_Iniciar.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        btn_Iniciar.setText("INICIAR");
        btn_Iniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_IniciarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_IniciarMouseExited(evt);
            }
        });
        btn_Iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_IniciarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_Iniciar, java.awt.BorderLayout.PAGE_END);

        btn_Detener.setBackground(new java.awt.Color(153, 153, 153));
        btn_Detener.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btn_Detener.setText("Detener");
        btn_Detener.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_DetenerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_DetenerMouseExited(evt);
            }
        });
        btn_Detener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DetenerActionPerformed(evt);
            }
        });
        jPanel6.add(btn_Detener, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.GridLayout(4, 2));

        lBl_Palabra_Oculta.setBackground(new java.awt.Color(75, 75, 75));
        lBl_Palabra_Oculta.setFont(new java.awt.Font("Noto Mono", 1, 18)); // NOI18N
        lBl_Palabra_Oculta.setForeground(new java.awt.Color(254, 254, 254));
        lBl_Palabra_Oculta.setText("Palabra Oculta");
        lBl_Palabra_Oculta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lBl_Palabra_Oculta.setOpaque(true);
        jPanel7.add(lBl_Palabra_Oculta);

        txtPalabra.setFont(new java.awt.Font("Noto Mono", 1, 18)); // NOI18N
        txtPalabra.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPalabraActionPerformed(evt);
            }
        });
        txtPalabra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPalabraKeyTyped(evt);
            }
        });
        jPanel7.add(txtPalabra);

        lbl_Pista.setBackground(new java.awt.Color(75, 75, 75));
        lbl_Pista.setFont(new java.awt.Font("Noto Mono", 1, 18)); // NOI18N
        lbl_Pista.setForeground(new java.awt.Color(254, 254, 254));
        lbl_Pista.setText("Pista");
        lbl_Pista.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbl_Pista.setOpaque(true);
        jPanel7.add(lbl_Pista);

        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Noto Mono", 1, 12)); // NOI18N
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel7.add(jScrollPane1);

        lbl_Adivinar.setBackground(new java.awt.Color(75, 75, 75));
        lbl_Adivinar.setFont(new java.awt.Font("Noto Mono", 1, 18)); // NOI18N
        lbl_Adivinar.setForeground(new java.awt.Color(254, 254, 254));
        lbl_Adivinar.setText("Adivinar");
        lbl_Adivinar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbl_Adivinar.setOpaque(true);
        jPanel7.add(lbl_Adivinar);

        txtAdivinar.setFont(new java.awt.Font("Noto Mono", 1, 18)); // NOI18N
        txtAdivinar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtAdivinar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdivinarActionPerformed(evt);
            }
        });
        txtAdivinar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAdivinarKeyTyped(evt);
            }
        });
        jPanel7.add(txtAdivinar);

        lbl_Palabras_Erradas.setBackground(new java.awt.Color(75, 75, 75));
        lbl_Palabras_Erradas.setFont(new java.awt.Font("Noto Mono", 1, 18)); // NOI18N
        lbl_Palabras_Erradas.setForeground(new java.awt.Color(254, 254, 254));
        lbl_Palabras_Erradas.setText("Letras Erradas                      ");
        lbl_Palabras_Erradas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbl_Palabras_Erradas.setOpaque(true);
        jPanel7.add(lbl_Palabras_Erradas);

        txtLetrasErradas.setFont(new java.awt.Font("Noto Mono", 1, 18)); // NOI18N
        txtLetrasErradas.setForeground(new java.awt.Color(255, 0, 0));
        txtLetrasErradas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtLetrasErradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLetrasErradasActionPerformed(evt);
            }
        });
        jPanel7.add(txtLetrasErradas);

        getContentPane().add(jPanel7, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPalabraActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPalabraActionPerformed

    private void txtAdivinarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdivinarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtAdivinarActionPerformed

    private void btn_IniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_IniciarActionPerformed
        // TODO add your handling code here:
        txtAdivinar.setEditable(true);  // Activará el cuadro de texto para ser editable
        btn_Detener.setEnabled(true);   // Activará el botón detener
        txtAdivinar.requestFocus(); // Mandará el editor de texto a la casilla donde el usuario deberá escribir las letras necesarias para poder jugar
        lbl_intentos.setText("Intentos: 1");    // Mostrará el número de intentos que lleva, en estecaso 1
        btn_Iniciar.setEnabled(false);  // Desactiva el botón de iniciar para que el usuario no pueda volver a presionarlo

        File archivo = new File("/home/juampa/Escritorio/Game-AhorcadoFin/Recursos_Ahorcado/archivo.txt");
        leerArchivo(archivo);   // Para leer el archivo y que pueda ser usado 
        loadNextWord(contador); // Método para ller las palabras en el archivo de texto


    }//GEN-LAST:event_btn_IniciarActionPerformed

    private void txtAdivinarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdivinarKeyTyped
        // TODO add your handling code here:
        // Método para traer la teclas que sean seleccionadas por el usuario
        char car = (evt.getKeyChar()); // se presionó una tecla

        if (!Character.isAlphabetic(car)) { // si no es un caracter
            evt.consume(); // No lo toma en cuenta, no los permite ingresar

        } else {    // Si es un caracter
            String carMayus = Character.toString(car).toUpperCase(); // Creación de una variable Cadena para convertir las pulsasiones del usuario a Mayúsculas

            int pos = palabra.indexOf(("" + carMayus));
            StringBuilder adivina = new StringBuilder();

            if (pos >= 0) {   // Significa que la letra sí está
                adivina.append(txtPalabra.getText());

                while (pos >= 0) {  // Significa que mientra se encuentre la letra

                    palabra.replace(pos, pos + 1, "_"); // Se convertirá la palabra a los guines dependiendo la longitud de la palabra
                    adivina.replace(pos, pos + 1, "" + carMayus);   // Se irá tranformando la palabra cuando las teclas se vallan pulsando
                    pos = palabra.indexOf(("" + carMayus));

                }
                txtPalabra.setText(adivina.toString()); // Se escribirá en la caja de texto las letras que se vallan pulsando y que sean correctas además de los guines si es que hacen falta
                txtAdivinar.setText("");    // Se limpiará el cuadro de texto
                cambiaPalabras();   // método para cambiar la palabra en caso de que esta sea acertada

            } else {

                
                

                evt.consume();  // Si la tecla pulsada no es correcta no se toma en cuenta
                intentos++; // Se incrementa el número de intentos
                txtLetrasErradas.setText(txtLetrasErradas.getText() + carMayus);    // Se escriba la letra que no fue correcta o no se encuentra en la palabra a adivinar en el cuadro de texto correspondiente
                cambiarIntento();       // método para cambiar las imágenes una vez que se compruebe el error de la tecla pulsada

            }

        }


    }//GEN-LAST:event_txtAdivinarKeyTyped

    private void txtLetrasErradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLetrasErradasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLetrasErradasActionPerformed

    private void txtPalabraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPalabraKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPalabraKeyTyped

    private void btn_IniciarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_IniciarMouseEntered
        // TODO add your handling code here:
        // Modificará el tamaño del bitón incrementando el tamaño y además modificando el color del mismo cuando el mouse pase sobre el botón
        btn_Iniciar.setBackground(new java.awt.Color(155, 155, 155));
        btn_Iniciar.setFont(new java.awt.Font("Noto Sans", 1, 20));
    }//GEN-LAST:event_btn_IniciarMouseEntered

    private void btn_IniciarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_IniciarMouseExited
        // TODO add your handling code here:
        // Regresa a la forma original del botón una vez que el mouse se quité de la posición del mismo
        btn_Iniciar.setBackground(new java.awt.Color(150, 150, 150));
        btn_Iniciar.setFont(new java.awt.Font("Noto Sans", 1, 18));

    }//GEN-LAST:event_btn_IniciarMouseExited

    private void btn_DetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DetenerActionPerformed
        // TODO add your handling code here:
        // Menú en caso de que el usuario deseé detener el juego en un tiempo determinado
        Object resp = JOptionPane.showInputDialog(null, "¿QUÉ DESEA HACER?", "                            "
                + "** Juego En Espera ** ", JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Continuar", "Reiniciar", "Salir"}, "Seleccione");

        if (resp.equals("Continuar")) {   // Continuará de manera normal el juego
            txtAdivinar.requestFocus();
            return;
        }
        if (resp.equals("Reiniciar")) {   // Terminará e iniciará un juego nuevo
            this.setVisible(false); // Borrará los datos y quitará la ventana del juego
            Ahorcado.main(null);
            return;
        }
        if (resp.equals("Salir")) {
            System.exit(0); // Fin de ejecución del programa
        }
    }//GEN-LAST:event_btn_DetenerActionPerformed

    private void btn_DetenerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_DetenerMouseEntered
        // TODO add your handling code here:
        // Modificará el tamaño del bitón incrementando el tamaño y además modificando el color del mismo, cuando el mouse pase sobre el botón
        btn_Detener.setBackground(new java.awt.Color(155, 155, 155));
        btn_Detener.setFont(new java.awt.Font("Noto Sans", 1, 20));
    }//GEN-LAST:event_btn_DetenerMouseEntered

    private void btn_DetenerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_DetenerMouseExited
        // TODO add your handling code here:
        // Regresa a la forma original del botón una vez que el mouse se quité de la posición del mismo
        btn_Detener.setBackground(new java.awt.Color(150, 150, 150));
        btn_Detener.setFont(new java.awt.Font("Noto Sans", 1, 18));
    }//GEN-LAST:event_btn_DetenerMouseExited

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
            java.util.logging.Logger.getLogger(Ahorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ahorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ahorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ahorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ahorcado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Detener;
    private javax.swing.JButton btn_Iniciar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lBl_Palabra_Oculta;
    private javax.swing.JLabel lbl_Adivinar;
    private javax.swing.JLabel lbl_Frase;
    private javax.swing.JLabel lbl_Imagen_Ahorcado;
    private javax.swing.JLabel lbl_Palabras_Erradas;
    private javax.swing.JLabel lbl_Pista;
    private javax.swing.JLabel lbl_intentos;
    private javax.swing.JTextField txtAdivinar;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtLetrasErradas;
    private javax.swing.JTextField txtPalabra;
    private javax.swing.JLabel txt_Titulo;
    // End of variables declaration//GEN-END:variables
    // Fin de la codificación
}
