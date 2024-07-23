import path from "path";

export const BASE_PATH = path.join(__dirname, "..", "..");

export const pathFromBase = (...nodes: string[]) => {
  return path.join(BASE_PATH, ...nodes);
};
