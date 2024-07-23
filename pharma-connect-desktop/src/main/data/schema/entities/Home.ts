import {
  Column,
  Entity,
  JoinColumn,
  OneToOne,
  PrimaryGeneratedColumn,
} from "typeorm";
import { Person } from "./Person";

@Entity()
export class Home {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @Column({})
  address: string;

  @OneToOne(() => Person, {
    eager: true,
  })
  @JoinColumn({ name: "owner_id" })
  owner: Person;
}
