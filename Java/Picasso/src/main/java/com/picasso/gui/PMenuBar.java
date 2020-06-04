package com.picasso.gui;

import com.picasso.app.FileMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PMenuBar extends JMenuBar {

    private PMenuBar() {
    }

    public static PMenuBar createMain() {
        PMenuBar menuBar = new PMenuBar();
        menuBar.setBackground(Theme.current().getMenu());
        menuBar.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));

        JMenu file = createMenu("File", 'F');
        file.add(createItem("Open...", FileMenu::open/*, KeyEvent.VK_O, KeyEvent.CTRL_MASK*/));
        file.add(createItem("Save"));
        menuBar.add(file);

        JMenu edit = createMenu("Edit", 'E');
        menuBar.add(edit);

        JMenu view = createMenu("View", 'V');
        menuBar.add(view);

        return menuBar;
    }

    private static JMenu createMenu(String name, char mnemonic) {
        JMenu menu = createMenu(name);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    private static JMenu createMenu(String name) {
        JMenu menu = new JMenu(name);
        applyCommonMenuStyle(menu);
        menu.setBorder(new EmptyBorder(new Insets(2, 2, 2, 2)));
        JPopupMenu popupMenu = menu.getPopupMenu();
        applyCommonMenuStyle(popupMenu);
        popupMenu.setBorder(new LineBorder(Theme.current().getMenuBorder()));
        return menu;
    }

    private static JMenuItem createItem(String name) {
        JMenuItem item = new JMenuItem(name);
        applyCommonMenuStyle(item);
        item.setBorder(new EmptyBorder(new Insets(2, 2, 2, 2)));
        return item;
    }

    private static JMenuItem createItem(String name, Runnable runnable) {
        JMenuItem item = createItem(name);
        item.addActionListener(e -> runnable.run());
        return item;
    }

    private static JMenuItem createItem(String name, Runnable runnable, int key, int mask) {
        JMenuItem item = createItem(name);
        item.addActionListener(e -> runnable.run());
        item.setAccelerator(KeyStroke.getKeyStroke(key, mask));
        return item;
    }

    private static void applyCommonMenuStyle(JComponent menu) {
        menu.setFont(Theme.current().getMainFont());
        menu.setForeground(Theme.current().getOnMenu());
        menu.setBackground(Theme.current().getMenu());
    }

}
