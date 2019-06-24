import soap from "soap-as-promised";
import {HOST, PORT, WSDL} from '../config'
import { commonService } from "./commonService";

const url = `http://${HOST}:${PORT}${WSDL.auth}`;

const loginUser = async user => {
  const client = await soap.createClient(url);
  const res = await client.loginUserAsync(user);
  const { response } = res[0];

  return response;
};

const reloginUser = async () => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.reloginUserAsync({ token });
  const { response } = res[0];
  return response;
};

const changePassword = async (currentPassword, newPassword) => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.changePasswordAsync({
    token,
    currentPassword,
    newPassword
  });
  const { response } = res[0];
  return response;
};

export const authService = {
  loginUser,
  reloginUser,
  changePassword
};
