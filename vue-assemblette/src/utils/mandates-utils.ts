import type { Mandate } from '@/entities/mandate'
import _ from 'lodash'

export default class MandatesUtils {
  public static getActiveMandatesForSpecificDate(mandates: Mandate[], date: string): Mandate[] {
    return _.filter(mandates, (mandate) => {
      return (
        mandate.startDate.localeCompare(date) <= 0 &&
        (!mandate.endDate || mandate.endDate.localeCompare(date) >= 0)
      )
    })
  }
}
