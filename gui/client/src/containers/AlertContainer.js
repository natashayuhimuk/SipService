import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import { Alert } from "../components/Common";
import { closeAlert } from "../actions";

const mapDispatchToProps = dispatch => ({
  closeAlert: () => closeAlert()(dispatch)
});

const mapStateToProps = state => ({
  ...state.alert
});

const AlertContainer = withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(Alert)
);

export default AlertContainer;
