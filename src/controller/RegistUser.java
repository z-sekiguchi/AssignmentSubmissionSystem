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

import model.UserBean;
import model.UserDao;

/**
 * Servlet implementation class RegistUser
 */
public class RegistUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int SYSTEM_ADMINISTRATOR = 1;		// システム管理者
	private int ADMINISTRATOR = 2;				// 管理者
	private int GENERAL_USER = 3;				// 一般ユーザー

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードを指定する
		request.setCharacterEncoding("UTF-8");

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

		UserDao ud = new UserDao();

		String name = (String)request.getParameter("userName");
		String password1 = (String)request.getParameter("password1");
		String password2 = (String)request.getParameter("password2");
		int privilege = request.getParameter("privilege") == null ?
						0 : Integer.parseInt((String)request.getParameter("privilege"));

		ArrayList<String> err = new ArrayList<>();

		// 全て入力されているかチェックする
		if(name.equals("")) {
			err.add("「名前」を入力してください。");
		}

		if(password1.equals("")) {
			err.add("「パスワード」を入力してください。");
		}

		if(password2.equals("")) {
			err.add("「パスワード(確認)」を入力してください。");
		}

		if(privilege == 0) {
			err.add("管理者権限を付与するか選択してください。");
		}

		if(err.size() == 0) {
			if(!ud.checkNameLength(name)) {
				err.add("名前は20文字以内で入力してください。");
			}

			if(!password1.equals(password2)) {
				err.add("「パスワード」と「パスワード(確認)」が一致しません。");
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

			if(!(privilege == ADMINISTRATOR || privilege == GENERAL_USER)) {
				err.add("権限が不正です");
			}
		}

		// err.size()が0ならユーザー登録を行う
		if(err.size() == 0) {
			UserBean regiBean = new UserBean();
			regiBean.setId(ud.getNextId());
			regiBean.setPassword(password1);
			regiBean.setName(name);
			regiBean.setPrivilege(privilege);
			String path = this.getServletContext().getRealPath("folders");
			int num = ud.registerDao(regiBean, path);
			if(num != 1) {
				err.add("ユーザー登録に失敗しました。");
			}
		}

		if(err.size() == 0) {
			request.setAttribute("result", "1");
			request.setAttribute("msg", "ユーザー登録に成功しました。");
		} else {
			request.setAttribute("result", "-1");
			request.setAttribute("err", err);
		}

		ArrayList<UserBean> ubList = ud.selectSearchDao("", GENERAL_USER);

		request.setAttribute("ubList", ubList);

		// 画面遷移(フォワードを使ってJSPに表示を切り替える)
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserManagement.jsp");
		dispatcher.forward(request, response);
	}

}
