package main;

import bean.LoginBean;
import constant.Config;
import login.LoginDialog;
import panel.admin.order.OrderManagePanel;
import panel.admin.part.PartManagePanel;
import panel.admin.user.UserManagePanel;
import panel.sup.login.LoginPanel;
import panel.sup.order.OrderPanel;
import panel.sup.part.PartPanel;
import panel.sup.user.UserPanel;
import panel.user.cart.ShopCartPanel;
import panel.user.order.MyOrderPanel;
import panel.user.parts.SelectPartsPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2020/03/24 12:56
 * @description 教师客户端主界面
 */
public class MainDialog extends JFrame {

    private LoginBean loginBean;
    private JPanel mainPanel;

    public MainDialog(LoginBean loginBean) {
        this.loginBean = loginBean;
        initView();
        addAbout();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (loginBean.getType() == Config.ADMIN_LOGIN) {
            setTitle("管理员客户端");
            initAdminMenuBar();
        } else if (loginBean.getType() == Config.USER_LOGIN) {
            setTitle("用户客户端");
            initUserMenuBar();
        } else if (loginBean.getType() == Config.SUPER_ADMIN_LOGIN) {
            setTitle("超级管理员客户端");
            initSuperAdminMenuBar();
        }
    }

    private void initUserMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu partsBankMenu = new JMenu("零件库");
        JMenuItem selectPartsMenuItem = new JMenuItem("查询零件");
        selectPartsMenuItem.addActionListener(event -> showPanel(new SelectPartsPanel(loginBean)));
        partsBankMenu.add(selectPartsMenuItem);

        JMenu shopCartMenu = new JMenu("购物车");
        JMenuItem shopCartMenuItem = new JMenuItem("查看购物车");
        shopCartMenuItem.addActionListener(event -> showPanel(new ShopCartPanel(loginBean)));
        shopCartMenu.add(shopCartMenuItem);

        JMenu orderMenu = new JMenu("订单");
        JMenuItem myOrderMenuItem = new JMenuItem("我的订单");
        myOrderMenuItem.addActionListener(event -> showPanel(new MyOrderPanel(loginBean)));
        orderMenu.add(myOrderMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem exitLoginMenuItem = new JMenuItem("退出登录");
        exitLoginMenuItem.addActionListener(event -> exitLogin());
        helpMenu.add(exitLoginMenuItem);

        menuBar.add(partsBankMenu);
        menuBar.add(shopCartMenu);
        menuBar.add(orderMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void initAdminMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu userMenu = new JMenu("用户管理");
        JMenuItem userMenuItem = new JMenuItem("查询用户");
        userMenuItem.addActionListener(event -> showPanel(new UserManagePanel()));
        userMenu.add(userMenuItem);

        JMenu partsMenu = new JMenu("零件管理");
        JMenuItem partMenuItem = new JMenuItem("零件管理");
        partMenuItem.addActionListener(event -> showPanel(new PartManagePanel()));
        partsMenu.add(partMenuItem);

        JMenu schemeMenu = new JMenu("方案决策");
        JMenuItem orderMenuItem = new JMenuItem("订单");
        orderMenuItem.addActionListener(event -> showPanel(new OrderManagePanel()));
        schemeMenu.add(orderMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem exitLoginMenuItem = new JMenuItem("退出登录");
        exitLoginMenuItem.addActionListener(event -> exitLogin());
        helpMenu.add(exitLoginMenuItem);

        menuBar.add(userMenu);
        menuBar.add(partsMenu);
        menuBar.add(schemeMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void initSuperAdminMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu userMenu = new JMenu("用户管理");
        JMenuItem loginMenuItem = new JMenuItem("登录账号");
        loginMenuItem.addActionListener(event -> showPanel(new LoginPanel()));
        JMenuItem userMenuItem = new JMenuItem("用户信息");
        userMenuItem.addActionListener(event -> showPanel(new UserPanel()));
        userMenu.add(loginMenuItem);
        userMenu.add(userMenuItem);

        JMenu partMenu = new JMenu("零件管理");
        JMenuItem partMenuItem = new JMenuItem("零件");
        partMenuItem.addActionListener(event -> showPanel(new PartPanel()));
        partMenu.add(partMenuItem);

        JMenu orderMenu = new JMenu("订单管理");
        JMenuItem shopMenuItem = new JMenuItem("购物车");
        shopMenuItem.addActionListener(event -> showPanel(new panel.sup.cart.ShopCartPanel()));
        JMenuItem orderMenuItem = new JMenuItem("订单");
        orderMenuItem.addActionListener(event -> showPanel(new OrderPanel()));
        orderMenu.add(shopMenuItem);
        orderMenu.add(orderMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem exitLoginMenuItem = new JMenuItem("退出登录");
        exitLoginMenuItem.addActionListener(event -> exitLogin());
        helpMenu.add(exitLoginMenuItem);

        menuBar.add(userMenu);
        menuBar.add(partMenu);
        menuBar.add(orderMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void addAbout() {
        JLabel about = new JLabel(Config.ABOUT_ME_STRING);
        mainPanel.add(about, BorderLayout.SOUTH);
    }

    private void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(panel, BorderLayout.CENTER);
        addAbout();
        mainPanel.revalidate();
    }

    private void exitLogin() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "确认退出当前界面？", "退出登录", JOptionPane.YES_NO_OPTION)) {
            this.dispose();
            new LoginDialog().setVisible(true);
        }
    }
}