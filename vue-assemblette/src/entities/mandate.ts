import type { Authority } from './authority'
import type { Deputy } from './deputy'

export interface Mandate {
  id: string
  deputy: Deputy
  authority: Authority
  startDate: string
  endDate: string
  seatNumber: number
}
