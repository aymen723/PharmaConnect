import type { ElectronHandler } from "../../../main/preload";

declare global {
  // eslint-disable-next-line @typescript-eslint/no-empty-interface
  interface Window {
    electron: ElectronHandler;
  }
}

export {};
