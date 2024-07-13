import * as React from "react";

import { cn } from "@/lib/utils";

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  StartIcon?: React.ReactNode;
  EndIcon?: React.ReactNode;
}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, StartIcon, EndIcon, ...props }, ref) => {
    return (
      <div className="relative">
        {StartIcon ? (
          <div className="absolute left-0 top-0 flex h-full items-center justify-center">
            {StartIcon}
          </div>
        ) : null}
        <input
          type={type}
          className={cn(
            "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-foreground disabled:cursor-not-allowed disabled:opacity-50",
            className,
          )}
          ref={ref}
          {...props}
        />
        {EndIcon ? (
          <div className="absolute right-0 top-0 flex h-full items-center justify-center">
            {EndIcon}
          </div>
        ) : null}
      </div>
    );
  },
);
Input.displayName = "Input";

export { Input };
