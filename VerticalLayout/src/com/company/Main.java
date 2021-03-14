package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class VerticalLayout implements LayoutManager {
    private Dimension size = new Dimension();
    //Два пустых метода
    public void addLayoutComponent(String name, Component comp) {}
    public void removeLayoutComponent(Component comp) {}
    // Метод определения минимального размера для контейнера
    public Dimension minimumLayoutSize(Container c) {
        return findRightSize(c);
    }
    // Метод определения предпочтительного размера для добавления в контейнер
    public Dimension preferredLayoutSize(Container c) {
        return findRightSize(c);
    }
    // Метод расположения компонентов в контейнере
    public void layoutContainer(Container container) {
        // Список компонентов
        Component listOfComponents[] = container.getComponents();
        int currentY = 5;
        for (int i = 0; i < listOfComponents.length; i++) {
            // Определение предпочтительного размера компонента, чтобы корректно расположить его в контейнере
            Dimension preferredSize = listOfComponents[i].getPreferredSize();
            // Размещение компонента на экране
            listOfComponents[i].setBounds(5, currentY, preferredSize.width, preferredSize.height);
            // Нахождение центра по оси абсцисс
            double xCenter = container.getWidth()/2 - 0.5*getPreferredSizeForComponent(container).width;
            listOfComponents[i].setBounds((int) xCenter, currentY, getPreferredSizeForComponent(container).width, getPreferredSizeForComponent(container).height);
            // Смещаем вертикальную позицию компонента и учитываем промежуток в 5 пикселей
            currentY += preferredSize.height + 10;
        }
    }
    // Метод вычисления оптимального размера контейнера
    private Dimension findRightSize(Container containerOfComponents) {
        // Вычисляем длину контейнера
        Component[] list = containerOfComponents.getComponents();
        int maxWidth = 0;
        for (int i = 0; i < list.length; i++) {
            int width = list[i].getWidth();
            // Поиск компонента с максимальной длиной
            if (width > maxWidth)
                maxWidth = width;
        }
        // Длина контейнера с учетом левого отступа
        size.width = maxWidth;
        // Вычисление высоты контейнера
        int height = 0;
        for (int i = 0; i < list.length; i++) {
            height += 5;
            height += list[i].getHeight();
        }
        size.height = height + 5;
        return size;
    }
    // Метод нахождения оптимального размера, который будет установлен всем компонентам
    public static Dimension getPreferredSizeForComponent(Container container) {
        Dimension preferredSize = new Dimension();
        int maxWidth = 0;
        int maxHeight = 0;
       // Находим максимальные высоту и ширину
        for (Component item: container.getComponents()) {
            if (maxHeight < item.getHeight())
                maxHeight = item.getHeight();

            if (maxWidth < item.getWidth())
                maxWidth = item.getWidth();
        }
        preferredSize.setSize(maxWidth, maxHeight);
        return preferredSize;
    }
}
public class Main {
    // Метод, задающий размеры JFrame и устанавливающий её в центре экрана при запуске
    static JFrame getFrame() {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize( 600, 600);
        jFrame.setLocationRelativeTo(null);
        return jFrame;
    }
    // JFrame, где мы будем располагать панель
    static JFrame frame = new JFrame();
    //Панель, на которой мы будем располагать наши компоненты
    static JPanel jPanelForComponents = new JPanel(new VerticalLayout());
    // Цвет лейблов
    static Color colorOfLable = Color.black;
    public static void main(String[] args) {
       // Установление размеров @param frame и расположение её по центру
        frame = getFrame();
       // Задаём Title нашей @param frame
        frame.setTitle("Тестирование вертикального расположения компонентов в контейнере");
       // Делаем нашу панель и frame одного размера
        jPanelForComponents.setSize(frame.getSize());
      // Задаём массив типов компонентов, которые будем размещать на нашей панели
        String[] namesTypesOfComponents = {
                "JButton",
                "JLable",
                "JTextField"
        };
        // Задаём массив режимов, в которых будет работать наша программа
        String[] nameOfModes = {
                "Режим добавления",
                "Режим удаления"
        };
        // Объявляем переменные лейбл, в которые занесём подсказки пользователю
        JLabel labelForCombobox = new JLabel("Выберите компонент, который хотите добавить на панель:");
        JLabel labelForModes = new JLabel("Выберите режим работы:");
        JLabel labelForTextOnComponents = new JLabel("Напишите текст, который будет располагаться на компоненте:");
        // JCombobox для хранения типов компонентов
        JComboBox conteinerForTypesOfComponents = new JComboBox(namesTypesOfComponents);
       // JCombobox для хранения режимов работы
        JComboBox modes = new JComboBox(nameOfModes);
       // Добавление лейбла для @param modes
        jPanelForComponents.add(labelForModes);
      // Добавление @param modes с выбором режима
        jPanelForComponents.add(modes);
        /* Добавление лейбла для @param conteinerOfComponents */
        jPanelForComponents.add(labelForCombobox);
        /* Добавление @param conteinerOfComponents , в котором будут находится названия компонентов */
        jPanelForComponents.add(conteinerForTypesOfComponents);
        jPanelForComponents.add(labelForTextOnComponents);
        JTextField nameOfComponent = new JTextField();
        // Добавляем текстовое поле, куда будем заносить текст, предназначенный для компонентов
        jPanelForComponents.add(nameOfComponent);
        // Кнопка добавления компонента
        JButton addComponent = new JButton("Добавить компонент");
        jPanelForComponents.add(addComponent);
        // Размещаем панель в контейнере
        frame.setContentPane(jPanelForComponents);
        // Открываем окно
        frame.setVisible(true);
        // Переопределяем метод закрытия окна, добавляем подтверждение о том, можно ли закрыть окно или нет
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = { "Да", "Нет" };
                int closeOrNot = JOptionPane.showOptionDialog(e.getWindow(), "Закрыть окно?","Подтверждение", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options,options[0]);
                if (closeOrNot == 0) {
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
                if (closeOrNot == 1) {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        addComponent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modes.getSelectedItem().equals("Режим добавления")) {
                    if (nameOfComponent.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Введите тест, который будет располагаться на компоненте!");
                    }
                    else {
                        // Добавление кнопки на панель
                        if (conteinerForTypesOfComponents.getSelectedItem() == "JButton") {
                            JButton newCreatedButton = new JButton(nameOfComponent.getText());
                            jPanelForComponents.add(newCreatedButton);
                        // Создание события, при котором кнопка удалится с панели
                            newCreatedButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(modes.getSelectedItem().equals("Режим удаления")){
                                        jPanelForComponents.remove(newCreatedButton);
                                        frame.setContentPane(jPanelForComponents);
                                    }
                                }
                            });
                            frame.setContentPane(jPanelForComponents);
                        }
                        // Добавление лейбла на панель
                        if (conteinerForTypesOfComponents.getSelectedItem() == "JLable") {
                            JLabel newCreatedLable = new JLabel(nameOfComponent.getText(), SwingConstants.CENTER);
                            newCreatedLable.setBorder(BorderFactory.createLineBorder(colorOfLable, 2));
                            newCreatedLable.setOpaque(true);
                            jPanelForComponents.add(newCreatedLable);
                            // При нажатии на компонент в режиме удаления он удаляется с панели
                            newCreatedLable.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    if (modes.getSelectedItem().equals("Режим удаления")) {
                                        jPanelForComponents.remove(newCreatedLable);
                                        frame.setContentPane(jPanelForComponents);
                                    }
                                }
                            });
                            frame.setContentPane(jPanelForComponents);
                        }
                        // Добавление текстового поля на панель
                        if (conteinerForTypesOfComponents.getSelectedItem() == "JTextField") {
                            JTextField newTextField = new JTextField(nameOfComponent.getText());
                            jPanelForComponents.add(newTextField);
                            // Удаление текстового поля при нажатии на неё в режиме удаления
                            newTextField.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        if(modes.getSelectedItem().equals("Режим удаления")){
                                            jPanelForComponents.remove(newTextField);
                                            frame.setContentPane(jPanelForComponents);
                                        }
                                    }
                                });

                            frame.setContentPane(jPanelForComponents);
                        }
                        frame.setContentPane(jPanelForComponents);
                        frame.setVisible(true);
                    }
                }
            }
        });
        // Всплывающая подсказка при наведении курсора на @param  modes
        modes.setToolTipText("В режиме удаления можно удалить компоненты красного цвета, кликнув на них мышкой!");
        // Изменение цвета компонентов при смене режима
        modes.addItemListener(new ItemListener() {
            @Override
           public void itemStateChanged(ItemEvent e) {
               if(modes.getSelectedItem().equals("Режим удаления")) {
                  // Задаем всем новым лейблам, которые находятся на панели, красный цвет
                   colorOfLable = Color.red;
                   Component[] listOfComponents = jPanelForComponents.getComponents();
                 // Задаем красный цвет всем компонентам панели
                   for (int i = 7; i < listOfComponents.length; i++) {
                       listOfComponents[i].setBackground(Color.red);
                   }
               }
               if(modes.getSelectedItem()=="Режим добавления"){
                   Component[] listOfComponents = jPanelForComponents.getComponents();
                  // Возвращаем цвет лейблов в режиме добавления
                   colorOfLable = Color.black;
                 // Возвращаем цвет компонентов в режиме добавления
                   for (int i=7; i<listOfComponents.length; i++){
                      if(listOfComponents[i] instanceof JTextField){
                          listOfComponents[i].setBackground(Color.white);
                      }
                      else listOfComponents[i].setBackground(UIManager.getColor("control"));
                   }
               }
           }
        });
    }
}