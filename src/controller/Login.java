package controller;

import java.io.IOException;

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
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// フィールド宣言
	private int SYSTEM_ADMINISTRATOR = 1;		// システム管理者
	private int ADMINISTRATOR = 2;				// 管理者
	private int GENERAL_USER = 3;				// 一般ユーザー

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 画面遷移(フォワードを使ってJSPに表示を切り替える)
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/Login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードを指定する
		request.setCharacterEncoding("UTF-8");

		// 遷移先を格納する変数
		String dist = "";

		int num = 0; // 認証されたユーザー数を格納する変数

		// セッションを取得する
		HttpSession session = request.getSession(false);

		// クライアントが送信したパラメータの取得
		String loginId = request.getParameter("loginId");					// ユーザーID
		String loginPassword = request.getParameter("loginPassword");		// パスワード

		// UserDaoクラスのインスタンスを生成する
		UserDao ud = new UserDao();

		// 指定したloginIdとloginPasswordに該当するユーザー数をnumに入れる
		num = ud.authDao(loginId, loginPassword);
		if(num <= 0) {
			dist = "/Login.jsp";
		} else {
			// ユーザー情報を取得しubに入れる
			UserBean ub = ud.getUserInfo(loginId);
			// セッションが存在するかチェックする
			if(session != null) {
				// 存在する場合、セッションを破棄する
				session.invalidate();
			}
			// セッションを開始する
			session = request.getSession(true);
			// サーバ側にauthとloginUserId, loginUserNameの値を保存する
			session.setAttribute("auth", "true");
			session.setAttribute("loginUserId", ub.getId());
			session.setAttribute("loginUserName", ub.getName());
			int privilege = ub.getPrivilege();
			session.setAttribute("loginUserPrivilege", String.valueOf(privilege));
			// システム管理者、管理者の場合
			if(privilege == SYSTEM_ADMINISTRATOR || privilege == ADMINISTRATOR) {
				response.sendRedirect("/AssignmentSubmissionSystem/StudentsList");
			} else if(privilege == GENERAL_USER) {
				response.sendRedirect("/AssignmentSubmissionSystem/Login");
			}
			return;
		}
		// jspへ結果のセット
		request.setAttribute("result", num);
		// 画面遷移(フォワードを使ってJSPに表示を切り替える)
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher(dist);
		dispatcher.forward(request, response);
	}
}
