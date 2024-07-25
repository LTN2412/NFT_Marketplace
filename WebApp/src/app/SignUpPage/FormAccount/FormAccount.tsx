import { Input } from "@/components/ui/input";
import UserIccon from "@/assets/User.svg?react";
import EmailIcon from "@/assets/Email.svg?react";
import LockIccon from "@/assets/Lock.svg?react";
export default function FormAccount() {
  return (
    <div className="bg-white">
      <Input
        placeholder="Username"
        StartIcon={<UserIccon className="w-10 fill-gray" />}
        className="bg-foreground"
      />
      <Input
        placeholder="Email Address"
        StartIcon={<EmailIcon className="w-10 fill-gray" />}
      />
      <Input
        placeholder="Password"
        StartIcon={<LockIccon className="w-10 fill-gray" />}
      />
      <Input
        placeholder="Confirm Password"
        StartIcon={<LockIccon className="w-10 fill-gray" />}
      />
    </div>
  );
}
