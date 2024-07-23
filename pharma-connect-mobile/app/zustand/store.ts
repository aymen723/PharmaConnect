import { create } from "zustand";
import { ProductRespData } from "../client/types/responses/StockResponses";
import {
  LoginResp,
  TokenData,
  UserProfile,
} from "../client/types/responses/authResponses";
import { UserType } from "../client/types/responses/Client";

export type CartItemtype = {
  product: ProductRespData;
  count: number;
};

type CartStore = {
  cart: CartItemtype[] | [];
  append: (object: CartItemtype) => void;
  deleteitem: (object: CartItemtype) => void;
  deleteAll: () => void;
  setCart: (item: CartItemtype[]) => void;
  increment: (productId: number) => void;
  decrement: (productId: number) => void;
};

type UserStore = {
  user: UserType | undefined;
  setUser: (user: UserType) => void;
  clearUser: () => void;
};

type TokenStore = {
  token: TokenData | undefined;
  setToken: (token: TokenData) => void;
  clearToken: () => void;
};

export const useCartStore = create<CartStore>((set) => ({
  cart: [],
  append: (object: CartItemtype) => {
    set((state) => {
      const exists = state.cart.some(
        (item) => item.product.id === object.product.id
      );
      if (!exists) {
        return { cart: [...state.cart, object] };
      }
      return state; // Return the state unchanged if the item already exists
    });
  },
  deleteitem: (object: CartItemtype) => {
    set((state) => ({
      cart: state.cart.filter((o) => o.product.id !== object.product.id),
    }));
  },
  setCart: (items: CartItemtype[]) => set({ cart: items }),
  deleteAll: () => set({ cart: [] }),

  increment: (productId: number) => {
    set((state) => ({
      cart: state.cart.map((item) =>
        item.product.id === productId && item.count < 5
          ? { ...item, count: item.count + 1 }
          : item
      ),
    }));
  },

  decrement: (productId: number) => {
    set((state) => ({
      cart: state.cart.map((item) =>
        item.product.id === productId && item.count > 1
          ? { ...item, count: item.count - 1 }
          : item
      ),
    }));
  },
}));

export const useUserStore = create<UserStore>((set) => ({
  user: undefined,
  setUser: (user: UserType) => set({ user }),
  clearUser: () => set({ user: undefined }),
}));

export const useTokenStore = create<TokenStore>((set) => ({
  token: undefined,
  setToken: (token: TokenData) => set({ token }),
  clearToken: () => set({ token: undefined }),
}));
