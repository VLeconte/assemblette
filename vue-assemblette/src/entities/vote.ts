import type { Ballot } from './ballot'
import type { Deputy } from './deputy'

export interface Vote {
  id: string
  ballot: Ballot
  deputy: Deputy
  state: string
}
