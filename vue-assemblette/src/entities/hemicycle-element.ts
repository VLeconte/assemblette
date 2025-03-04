import type { Authority } from './authority'
import type { Deputy } from './deputy'
import type { Mandate } from './mandate'

export interface HemicycleElement {
  deputy: Deputy
  mandateAssembly: Mandate
  authorityPG: Authority
}
