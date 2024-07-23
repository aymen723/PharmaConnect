import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export class Person {
  @PrimaryGeneratedColumn({ name: "mapper_id" })
  id: number;

  @Column({ name: "mapper_name", type: "varchar" })
  name: string;

  @Column({ name: "mapper_email" })
  email: string;
}
