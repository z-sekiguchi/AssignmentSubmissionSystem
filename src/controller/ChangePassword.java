package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDao;

/**
 * Servlet implementation class ChangePassword
 */
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);

		// ログインしていないかチェックする
		if(session == null || session.getAttribute("auth") == null) {
			// ログインしていない場合、ログイン画面へ遷移する
			// jspへ結果のセット
			request.setAttribute("result", -2);
			// 画面遷移(フォワードを使ってJSPに表示を切り替える)
			ServletContext app = this.getServletContext();
			RequestDispatcher dispatcher = app.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
			return;
		};

		// 画面遷移(フォワードを使ってJSPに表示を切り替える)
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/ChangePassword.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);

		// ログインしていないかチェックする
		if(session == null || session.getAttribute("auth") == null) {
			// ログインしていない場合、ログイン画面へ遷移する
			// jspへ結果のセット
			request.setAttribute("result", -2);
			// 画面遷移(フォワードを使ってJSPに表示を切り替える)
			ServletContext app = this.getServletContext();
			RequestDispatcher dispatcher = app.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
			return;
		};

		// 文字コードを指定する
		request.setCharacterEncoding("UTF-8");

		// クライアントが送信したパラメータの取得
		String defaultPassword = request.getParameter("defaultPassword");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		ArrayList<String> err = new ArrayList<>();

		UserDao ud = new UserDao();
		String loginId = (String)session.getAttribute("loginUserId");
		// 全て入力されているかチェックする
		if(defaultPassword.equals("")) {
			err.add("「現在のパスワード」を入力してください。");
		}

		if(password1.equals("")) {
			err.add("「新しいパスワード」を入力してください。");
		}

		if(password2.equals("")) {
			err.add("「新しいパスワード(確認)」を入力してください。");
		}

		// err.size()が0なら以下のチェックを行う
		if(err.size() == 0) {
			// 現在のパスワードが正しいかチェックする
			if(ud.authDao(loginId, defaultPassword) == 0) {
				err.add("現在のパスワードを正しく入力してください。");
			};

			if(!password1.equals(password2)) {
				err.add("「新しいパスワード」と「新しいパスワード(確認)」が一致しません。");
			}

			if(defaultPassword.equals(password1)) {
				err.add("新しいパスワードが現在のパスワードと一致しています。");
			}

			if(!ud.checkPasswordLength(password1)) {
				err.add("パスワードは8文字以上、16文字以下で入力してください。");
			}

			if(!ud.checkPasswordString(password1)) {
				err.add("パスワードは半角英数記号で入力してください。");
			}

			if(!ud.checkPasswordStringComb(password1)) {
				err.add("パスワードは英字および数字を1文字以上含めてください。");
			}
		}

		// err.size()が0ならパスワードの変更を行う
		if(err.size() == 0) {
			int num = ud.changePassword(loginId, password1);
			if(num == 0) {
				err.add("パスワードの更新に失敗しました。");
			}
		}

		if(err.size() == 0) {
			request.setAttribute("result", "1");
			request.setAttribute("msg", "パスワードを変更しました。");
		} else {
			request.setAttribute("result", "-1");
			request.setAttribute("err", err);
		}

		// 画面遷移(フォワードを使ってJSPに表示を切り替える)
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/ChangePassword.jsp");
		dispatcher.forward(request, response);
	}

}
