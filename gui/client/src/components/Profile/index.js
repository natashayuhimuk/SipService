import React, { useEffect, useState } from "react";

import Card from "@material-ui/core/Card";
import List from "@material-ui/core/List";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import IconButton from "@material-ui/core/IconButton";
import Icon from "@material-ui/core/Icon";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";

import { Modal } from "../Common";
import CardChangePassword from "./CardChangePassword";
import ListItem from "./ListItem";
import { commonService, tariffService, userService } from "../../services";
import { MESSAGE, VALID } from "../../config";

const styles = theme => ({
  card: {
    minWidth: 275
  },
  header: {
    paddingBottom: 0
  },
  item: {
    padding: "5px",
    display: "inline-block"
  },
  cardContent: {
    paddingTop: 0,
    paddingBottom: 0
  },
  iconButton: {
    fontSize: 20,
    marginLeft: theme.spacing.unit
  }
});

const Profile = ({ classes, user, updateUser, openAlert }) => {
  const [open, setOpen] = useState(false);
  const [isEditProfile, setEditProfile] = useState(false);
  const [valid, setValid] = useState([]);

  const [firstName, setFirstName] = useState(user.firstName);
  const [lastName, setLastName] = useState(user.lastName);
  const [secondName, setSecondName] = useState(user.secondName);
  const [tariffName, setTariffName] = useState(user.tariff.name);
  const [balance, setBalance] = useState(user.balance);

  const [tariffs, setTariffs] = useState([]);

  useEffect(() => getTariffs(), []);
  useEffect(() => setValidTrue(), []);
  useEffect(() => getBalance(), []);

  const getTariffs = () => {
    tariffService.getTariffs().then(({ tariffs, success, message }) => {
      if (success) {
        setTariffs(tariffs);
      } else {
        openAlert(message, success);
      }
    });
  };
  const getBalance = () => {
    userService.getBalance().then(balance => {
      setBalance(balance);
    });
  };

  const setValidTrue = () => {
    const valid = conf.map(() => true);
    setValid(valid);
  };

  const onValid = () => {
    const valid = conf.map(({ value, regexp = "" }) =>
      new RegExp(regexp).test(value)
    );
    setValid(valid);
    return valid.every(item => item);
  };

  const onSubmit = () => {
    if (onValid()) {
      const user = {
        firstName,
        lastName,
        secondName,
        tariff: tariffName,
        balance
      };
      updateUser(user);
      setEditProfile(false);
    }
  };

  const conf = [
    {
      title: "First Name",
      value: firstName,
      setValue: setFirstName,
      regexp: VALID.name,
      message: MESSAGE.ERROR.name
    },
    {
      title: "Last Name",
      value: lastName,
      setValue: setLastName,
      regexp: VALID.name,
      message: MESSAGE.ERROR.name
    },
    {
      title: "Second Name",
      value: secondName,
      setValue: setSecondName,
      regexp: VALID.name,
      message: MESSAGE.ERROR.name
    },
    {
      title: "Tariff",
      value: tariffName,
      setValue: setTariffName
    },
    {
      title: "Balance",
      value: balance,
      setValue: setBalance,
      regexp: VALID.number,
      message: MESSAGE.ERROR.number
    }
  ];

  const handleCancel = () => {
    setFirstName(user.firstName);
    setLastName(user.lastName);
    setSecondName(user.secondName);
    setTariffName(user.tariff.name);
    setBalance(user.balance);
    setEditProfile(false);
    setValidTrue();
  };

  return (
    <div>
      <Card className={classes.card}>
        <CardHeader
          action={
            <IconButton onClick={() => setOpen(true)}>
              <Icon>vpn_key</Icon>
            </IconButton>
          }
          title={
            <Typography variant="h6">
              {commonService.parseNumber(user.phoneNumber)}
            </Typography>
          }
          className={classes.header}
        />
        <CardContent className={classes.cardContent}>
          <List>
            {conf.map((item, i) => (
              <ListItem
                key={item.title}
                primary={item.title}
                secondary={item.value}
                messageError={item.message}
                setEditProfile={setEditProfile}
                setState={item.setValue}
                list={item.title === "Tariff" ? tariffs : []}
                valid={valid[i]}
              />
            ))}
          </List>
        </CardContent>
        {isEditProfile && (
          <CardActions>
            <Button size="small" color="primary" onClick={onSubmit}>
              Save
            </Button>
            <Button size="small" color="secondary" onClick={handleCancel}>
              Cancel
            </Button>
          </CardActions>
        )}
      </Card>
      <Modal
        open={open}
        content={
          <CardChangePassword
            user={user}
            setOpen={setOpen}
            updateUser={updateUser}
            openAlert={openAlert}
            login={user.phoneNumber}
          />
        }
      />
    </div>
  );
};

export default withStyles(styles)(Profile);
