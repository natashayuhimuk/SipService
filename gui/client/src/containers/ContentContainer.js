import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import Content from "../components/Content";

const mapStateToProps = state => ({
  user: state.user
});

const ContentContainer = withRouter(connect(mapStateToProps)(Content));

export default ContentContainer;
