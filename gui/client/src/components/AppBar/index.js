import React from "react";

import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import Hidden from "@material-ui/core/Hidden";
import Icon from "@material-ui/core/Icon";
import { withStyles } from "@material-ui/core/styles";
import LinearProgress from "@material-ui/core/LinearProgress";

const styles = theme => ({
  appBar: {
    zIndex: theme.zIndex.drawer + 1
  },
  grow: {
    flexGrow: 1
  },
  menuButton: {
    marginLeft: -10,
    marginRight: 5
  },
  iconButton: {
    marginLeft: 10,
    marginRight: -10
  }
});

const Header = ({
  classes,
  changeDrawer,
  signOut,
  user,
  loading
}) => (
  <AppBar position="fixed" className={classes.appBar}>
    <Toolbar>
      <Hidden mdUp>
        <IconButton
          className={classes.menuButton}
          onClick={() => changeDrawer(true)}
          color="inherit"
        >
          <Icon>menu_icon</Icon>
        </IconButton>
      </Hidden>
      <Typography variant="h5" color="inherit" className={classes.grow}>
        Sip service
      </Typography>
      <Typography variant="h6" color="inherit">
        {user.firstName}
      </Typography>
      <IconButton
        color="inherit"
        className={classes.iconButton}
        onClick={signOut}
      >
        <Icon>exit_to_app</Icon>
      </IconButton>
    </Toolbar>
    {loading && <LinearProgress />}
  </AppBar>
);

export default withStyles(styles)(Header);
