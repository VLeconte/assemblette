import type { Mandate } from '@/entities/mandate'
import { defineStore } from 'pinia'
import { reactive } from 'vue'
import _ from 'lodash'
import MandatesService from '@/services/mandates-service'

export const useMainStore = defineStore('main', () => {
  const mandatesService = new MandatesService()
  const mandatesByDeputies = reactive<{
    data: Record<string, Mandate[]>
    isLoading: boolean
  }>({
    data: {},
    isLoading: true,
  })

  async function getMandatesByDeputies() {
    if (_.isEmpty(mandatesByDeputies.data)) {
      try {
        mandatesByDeputies.isLoading = true
        const mandates: Mandate[] = await mandatesService.getMandates()
        mandatesByDeputies.data = _.groupBy(mandates, (mandate) => mandate.deputy.id)
        mandatesByDeputies.isLoading = false
      } catch (error) {
        console.error('Error fetching deputies', error)
      }
    }
  }

  return { mandatesByDeputies, getMandatesByDeputies }
})
