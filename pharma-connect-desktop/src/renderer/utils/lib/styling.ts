import { twMerge } from "tailwind-merge";
import { clsx, ClassValue } from "clsx";

export function cn(...values: ClassValue[]): string {
  return twMerge(clsx(values));
}
