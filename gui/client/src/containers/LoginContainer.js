import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import Login from "../components/Login";
import { signIn } from "../actions";

const mapDispatchToProps = dispatch => ({
  onSignIn: (number, password, remember) =>
    signIn(number, password, remember)(dispatch)
});

const mapStateToProps = state => ({});

const LoginContainer = withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(Login)
);

export default LoginContainer;
