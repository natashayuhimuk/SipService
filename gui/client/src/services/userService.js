import soap from "soap-as-promised";
import {HOST, PORT, WSDL} from '../config'
import { commonService } from "./commonService";

const url = `http://${HOST}:${PORT}${WSDL.user}`;

const createUser = async user => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.createUserAsync({ token, user });
  const { response } = res[0];
  return response;
};

const updateUser = async user => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.updateUserAsync({ token, user });
  const { response } = res[0];
  return response;
};

const updateUserByAdmin = async user => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.updateUserByAdminAsync({ token, user });
  const { response } = res[0];
  return response;
};

const deleteUser = async users => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.deleteUsersAsync({ token, users });
  const { response } = res[0];
  return response;
};

const getAllUsers = async () => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.getAllUsersAsync({ token });
  const { response } = res[0];
  return response;
};

const getBalance = async () => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.getUserBalance({ token });
  const { response } = res;
  return response;
};

export const userService = {
  createUser,
  updateUser,
  deleteUser,
  getAllUsers,
  updateUserByAdmin,
  getBalance
};
