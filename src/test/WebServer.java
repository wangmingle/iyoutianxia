package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

import com.firefly.server.ServerBootstrap;

public class WebServer extends JTextArea {
	public WebServer(InputStream[] inStreams) {
		for (int i = 0; i < inStreams.length; ++i)
			startConsoleReaderThread(inStreams[i]);
	} // ConsoleTextArea()

	public WebServer() throws IOException {
		final LoopedStreams ls = new LoopedStreams();
		// 重定向System.out和System.err
		PrintStream ps = new PrintStream(ls.getOutputStream());
		System.setOut(ps);
		System.setErr(ps);
		startConsoleReaderThread(ls.getInputStream());
	} // ConsoleTextArea()

	private void startConsoleReaderThread(InputStream inStream) {
		final BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		new Thread(new Runnable() {
			public void run() {
				StringBuffer sb = new StringBuffer();
				try {
					String s;
					Document doc = getDocument();
					while ((s = br.readLine()) != null) {
						boolean caretAtEnd = false;
						caretAtEnd = getCaretPosition() == doc.getLength() ? true : false;
						sb.setLength(0);
						append(sb.append(s).append('\n').toString());
						if (caretAtEnd)
							setCaretPosition(doc.getLength());
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "从BufferedReader读取错误：" + e);
					System.exit(1);
				}
			}
		}).start();
	} // startConsoleReaderThread()

	// 该类剩余部分的功能是进行测试
	public static void main(String[] args) {
		JFrame f = new JFrame("VicServer Beta");
		WebServer consoleTextArea = null;
		try {
			consoleTextArea = new WebServer();
		} catch (IOException e) {
			System.err.println("不能创建LoopedStreams：" + e);
			System.exit(1);
		}
		consoleTextArea.setFont(java.awt.Font.decode("monospaced"));
		f.getContentPane().add(new JScrollPane(consoleTextArea), java.awt.BorderLayout.CENTER);
		f.setBounds(50, 50, 800, 500);
		f.setVisible(true);
		f.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				System.exit(0);
			}
		});
		ServerBootstrap.start("firefly.xml");
	}

	/**private static void startWriterTestThread(final String name, final PrintStream ps, final int delay, final int count) {
		new Thread(new Runnable() {
			public void run() {
				//for (int i = 1; i <= count; ++i) {
					ps.println("***" + name + ", hello !, i=" + i);
					//try {
					// Thread.sleep(delay);
					//} catch (InterruptedException e) {
					//}
				}
			}
		}).start();
 	}*/
}
