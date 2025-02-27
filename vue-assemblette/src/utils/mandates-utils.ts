import type { Mandate } from '@/entities/mandate'
import _ from 'lodash'

export function getActiveMandatesForSpecificDate(mandates: Mandate[], date: string): Mandate[] {
  return _.filter(mandates, (mandate) => {
    return (
      mandate.startDate.localeCompare(date) <= 0 &&
      (!mandate.endDate || mandate.endDate.localeCompare(date) >= 0)
    )
  })
}

export function capitalizeFirstLetter(val: string) {
  return String(val).charAt(0).toUpperCase() + String(val).slice(1)
}
