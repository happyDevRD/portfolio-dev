import { Skill } from './skill.model';

export interface SkillGroup {
  category: string;
  items: Skill[];
}
