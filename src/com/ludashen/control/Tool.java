package com.ludashen.control;




import com.ludashen.frame.AdminFrame;
import com.ludashen.hothl.Comment;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description: 自定义工具类，用于解决重复代码的各种功能
 * @author: 陆均琪
 * @Data: 2019-11-25 18:04
 */

public class Tool {



	public static RTextField jTextField(int x,int y,JPanel p,int... bounds){
		/**
		 * @description: 文本输入框控件，只能在空布局中用
		 * @param x		x轴坐标
		 * @param y		y轴坐标
		 * @param p		把控件添加到某个面板中
		 * @return: javax.swing.JTextField
		 * @author: 陆均琪
		 * @time: 2019-12-04 22:06
		 */

		RTextField textField = new RTextField();
		textField.setLocation(x,y);
		setBounds(textField,bounds);
		textField.setColumns(10);
		p.add(textField);
		return textField;
	}
	
	public static RButton rButton(int x,int y,String test,JPanel p,int...bounds){
		/**
		 * @description: 按钮控件
		 * @param x	x轴坐标
		 * @param y	y轴坐标
		 * @param test	按钮显示的文本
		 * @param p	把控件添加到面板中
		 * @return: com.ludashen.control.RButton
		 * @author: 陆均琪
		 * @time: 2019-12-04 22:08
		 */
		RButton btnNewButton = new RButton(test);
		btnNewButton.setLocation(x,y);
		setBounds(btnNewButton,bounds);
		p.add(btnNewButton);
		return btnNewButton;
	}

	public static JLabel jLabel(int x,int y,String text,JPanel p,int... bounds)  {
		JLabel jLabel = new JLabel(text);
		jLabel.setLocation(x, y);
		setBounds(jLabel, bounds);
		jLabel.setForeground(Color.ORANGE);
		p.add(jLabel);
		return jLabel;
	}

		public static RPasswordField passwordField(int x,int y,char Echo,JPanel p,int... bounds){
		/**
		 * @description:  密码输入框控件
		 * @param x	x轴坐标
		 * @param y	y轴坐标
		 * @param char Echo 输入后的密文显示
		 * @param p	面板
		 * @return: javax.swing.JPasswordField
		 * @author: 陆均琪
		 * @time: 2019-12-05 13:36
		 */

		RPasswordField ps=new RPasswordField();
		ps.setEchoChar(Echo);
		ps.setLocation(x,y);
		setBounds(ps,bounds);
//			ps.setBorder(null);// 取消边框
//			ps.setOpaque(false);// 设置控件透明
		p.add(ps);
		return ps;

	}
	public static RTextField chooserFile(int x,int y,JPanel p,int... bounds){
		/**
		 * @description: 文件选择输入框
		 * @param x x轴坐标
		 * @param y y轴坐标
		 * @param p 面板
		 * @param bounds 宽高
		 * @return: javax.swing.JTextField
		 * @author: 陆均琪
		 * @time: 2019-12-07 9:21
		 */
		RTextField browse= new FileChooser();
		browse.setLocation(x,y);
		setBounds(browse,bounds);
		browse.setEnabled(false);
		browse.setText("点击浏览");
		p.add(browse);
		return browse;
	}


	public static JTable jTable(int x,int y,int w,int h,JPanel p){
		RTable table=new RTable();
		RScrollPane jScrollPane=new RScrollPane(table,"");
		jScrollPane.setBounds(x,y,w,h);
		jScrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		p.add(jScrollPane);
		return table;

	}

	public static JList jList(int x,int y,int w,int h,JPanel p){
		JList list = new JList();
		list.setOpaque(false);
		list.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		RScrollPane jp = new RScrollPane(list,"");
		jp.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		jp.getHorizontalScrollBar().setUI(new DemoScrollBarUI());
		jp.setBounds(x,y,w,h);
		p.add(jp);
		return list;
	}

    public static JButton getFunButton(int x, int y, String path1, String path2, ActionListener l) {
        /**
         * @description: 会改变图标的按钮
         * @param x 面板布局X轴
         * @param y 面板布局y轴
         * @param path1 设置按钮图标路径
         * @param path2 设置鼠标经过按钮的图标路径
         * @param l 按钮按下监控事件
         * @return: javax.swing.JButton
         * @author: 陆均琪
         * @time: 2019-11-25 21:46
         */
        JButton funButton = new JButton();
        funButton.setBounds(new Rectangle(x, y, 40, 30));
        funButton.setIcon(new ImageIcon(Tool.class.getResource(
                path1)));
        funButton.setContentAreaFilled(false);
        funButton.setRolloverIcon(new ImageIcon(Tool.class.getResource(
                path2)));
        funButton.setOpaque(false);// 设置透明
        funButton.setBorder(null);// 取消边框
        funButton.addActionListener(l);
        return funButton;
    }
	public static String toSex(Boolean b){
		/**
		 * @description: 传入布尔值转为对应的字符
		 * @param b 布尔值	true女生	false 男生
		 * @return: java.lang.String
		 * @author: 陆均琪
		 * @time: 2019-12-04 22:10
		 */

		if(b)
			return "女";
		else
			return "男";
	}

	public static Boolean isDate(int count){
		/**
		 * @description: 数据库各种操作时用，当数据大于0时返回true，就是数据库执行成功
		 * @param count	数据库执行后返回的数值
		 * @return: java.lang.Boolean
		 * @author: 陆均琪
		 * @time: 2019-12-04 22:11
		 */

		if(count>0)
			return true;
		else
			return false;
	}

	public static Date strToDate(String strDate) {
		/**
		 * @description: 把字符串转换为Date类型，用在数据库生日的存储
		 * @param strDate 短字符只能是：2019-01-10的格式
		 * @return: java.util.Date
		 * @author: 陆均琪
		 * @time: 2019-12-04 22:13
		 */

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static Boolean getSex(String choice){
		/**
		 * @description: 把性别字符串转为布尔值
		 * @param choice 男 返回false  女 返回 true
		 * @return: java.lang.Boolean
		 * @author: 陆均琪
		 * @time: 2019-12-04 22:14
		 */

		switch (choice.trim()){
			case "男":
				return false;
			case "女" :
				return true;
			default:
				return false;
		}
	}

	public static JTabbedPane jTabbedPane(int x,int y,JPanel p,int... bounds){
		/**
		 * @description: 设置切换面板
		 * @param x	x轴
		 * @param y	y轴
		 * @param p	面板
		 * @param bounds	大小
		 * @return: javax.swing.JTabbedPane
		 * @author: 陆均琪
		 * @time: 2019-12-07 10:15
		 */
		JTabbedPane jTabbedPane= new JTabbedPane();
		jTabbedPane.setLocation(x,y);
		setBounds(jTabbedPane,bounds);
		p.add(jTabbedPane);
		return jTabbedPane;
	}

	public static void Tabp(JTabbedPane jTabbedPane,String title, String img,Component component, String ti) {
		/**
		 * @description:   切换卡菜单
		 * @param title 设置标题
		 * @param img   设置图片
		 * @param component    添加空键
		 * @param ti    鼠标移动到的提示
		 * @return: javax.swing.JTabbedPane
		 * @author: 陆均琪
		 * @time: 2019-11-26 0:33
		 */
		ImageIcon imm = new ImageIcon(AdminFrame.class.getResource(img));
		jTabbedPane.addTab(title, imm, component, ti);
	}

	public static RTextField calendarChoose (int x, int y, Date date, String test, JPanel p, int... bounds){
		Chooser chooser = Chooser.getInstance(date);
		RTextField jTextField =new RTextField();
		jTextField.setText(test);
		chooser.register(jTextField);
		jTextField.setLocation(x,y);
		setBounds(jTextField,bounds);
		chooser.setBorder(null);// 取消边框
		chooser.setOpaque(false);// 设置控件透明
		p.add(jTextField);
		return jTextField;
	}

	private static void setBounds(Component comp,int... bounds){
		/**
		 * @description: 设置控件大小
		 * @param comp	控件
		 * @param bounds	控件的宽或高
		 * @return: void
		 * @author: 陆均琪
		 * @time: 2019-12-07 10:13
		 */
		comp.setSize(120,30);
		if(bounds.length==1)
			comp.setSize(bounds[0],30);
		if(bounds.length==2)
			comp.setSize(bounds[0],bounds[1]);
	}

	public static String dateToStr(Date date,String... str){
		/**
		 * @description: 日期类型转换为字符串
		 * @param date
		 * @param str
		 * @return: java.lang.String
		 * @author: 陆均琪
		 * @time: 2019-12-07 10:13
		 */
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(str.length==1)
			sdf = new SimpleDateFormat(str[0]);
		return sdf.format(date);
	}


	public static void SystemTrayInitial(JFrame frame){
		/**
		 * @description: 创建系统托盘
		 * @param
		 * @return: void
		 * @author: 陆均琪
		 * @time: 2019-11-26 0:27
		 */
		JMenuBar menuBar;  //菜单栏
		TrayIcon trayIcon;  //系统托盘
		if(!SystemTray.isSupported()){
			return;
		}
		try {
			String title="酒店系统";
			String conn="欢迎使用酒店系统";
			SystemTray systemTray=SystemTray.getSystemTray();//获取系统托盘
			Image image=Toolkit.getDefaultToolkit().getImage(AdminFrame.class.getResource("/resource/log.png"));
			trayIcon=new TrayIcon(image,title+"\n"+conn,creaMenu(frame));
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(e -> {
				frame.setVisible(true);
				frame.toFront();
			});
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private static PopupMenu creaMenu(JFrame frame){
		/**
		 * @description: 系统托盘菜单的各种执行事件
		 * @param
		 * @return: java.awt.PopupMenu
		 * @author: 陆均琪
		 * @time: 2019-11-26 0:26
		 */
		PopupMenu menu=new PopupMenu();
		MenuItem ex=new MenuItem("Exit");
		ex.addActionListener(e -> System.exit(0));
		MenuItem op=new MenuItem("OPEN");
		op.addActionListener(e -> {
			if (frame.isVisible()){
				frame.setVisible(true);
				frame.toFront();
			}else
				frame.toFront();
		});
		menu.add(ex);
		menu.add(op);
		return menu;
	}

	public static void comment(JTable table, List<Comment> comm){
		/**
		 * @description: 客房表格刷新
		 * @param sq  1 查看全部客房 2查看空的客房 3查看有客户预定的客房
		 * @return: void
		 * @author: 陆均琪
		 * @time: 2019-12-04 23:46
		 */
		String[] chead={"id","用户","房子","评论","时间"};  //客房表格
		int i=0;
		List<Comment> comments =comm;
		Object[] [] resu=new Object[comments.size()][5];//*x行 4列
		i=0;
		for (Comment comment:comments){
			resu[i][0]=comment.getId();
			resu[i][1]=comment.getUid();
			resu[i][2]=comment.getHid();
			resu[i][3]=comment.getComment();
			resu[i][4]=comment.getDate();
			i++;
		}
		DefaultTableModel model=new DefaultTableModel();
		model.setDataVector(resu,chead);
		table.setModel(model);
	}





}

