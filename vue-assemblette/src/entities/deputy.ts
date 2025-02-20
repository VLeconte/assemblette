import type { Mandate } from './mandate'

export interface Deputy {
  id: string
  firstName: string
  lastName: string
  profession: string
  mandates: Mandate[]
}
