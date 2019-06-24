import soap from "soap-as-promised";
import {HOST, PORT, WSDL} from '../config'
import { commonService } from "./commonService";

const url = `http://${HOST}:${PORT}${WSDL.blackList}`;

const getAll = async () => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.getBlackListAsync({ token });
  const { response } = res[0];
  return response;
};

const getByNumber = async phoneNumber => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.getBlackListByAdminAsync({ token, phoneNumber });
  const { response } = res[0];
  return response;
};

const deleteNumbers = async phoneNumbers => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.deleteFromBlackListAsync({ token, phoneNumbers });
  const { response } = res[0];
  return response;
};

const addNumber = async phoneNumber => {
  const token = commonService.getToken();
  const client = await soap.createClient(url);
  const res = await client.addToBlackListAsync({ token, phoneNumber });
  const { response } = res[0];
  return response;
};

export const blackListService = {
  getAll,
  deleteNumbers,
  getByNumber,
  addNumber
};
