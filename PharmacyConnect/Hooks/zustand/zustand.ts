import { LoginResp } from "@/client/types/responses/authResponses";
import { create } from "zustand";

type UserStore = {
  user: LoginResp | undefined;
  setUser: (user: LoginResp) => void;
  clearUser: () => void;
};
export const useUserStore = create<UserStore>((set) => ({
  user: undefined,
  setUser: (user: LoginResp) => set({ user }),
  clearUser: () => set({ user: undefined }),
}));
