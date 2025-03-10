<script setup lang="ts">
import { onMounted, ref } from 'vue';
import DeputyCard from '@/components/DeputyCard.vue';
import { type Deputy } from '@/entities/deputy';
import { useDeputiesStore } from '@/store/store-deputies';
import { useMandatesStore } from '@/store/store-mandates';
import type { Mandate } from '@/entities/mandate';
import type { HemicycleElement } from '@/entities/hemicycle-element';
import { getActiveDeputiesForSpecificDate, getAuhtorityPGForSpecificDate } from '@/utils/deputies-utils';
import _ from 'lodash'
import { getActiveMandatesForSpecificDate } from '@/utils/mandates-utils';
import HemicycleChart from '@/components/HemicycleChart.vue';
import { useAuthoritiesStore } from '@/store/store-authorities';
import type { Authority } from '@/entities/authority';
import HemicyleElementFullInfo from '@/components/HemicyleElementFullInfo.vue';

const deputiesStore = useDeputiesStore()
const mandatesStore = useMandatesStore()
const authoritiesStore = useAuthoritiesStore()

const deputies = ref<Deputy[]>([])
const mandatesByDeputies = ref<Record<string, Mandate[]>>({})
const hemicycleElements = ref<HemicycleElement[]>([])
const authorities = ref<Authority[]>([]);
const loadingData = ref(true)


onMounted(
  async () => {
    deputies.value = await deputiesStore.getDeputies()
    const mandates = await mandatesStore.getMandates()
    authorities.value = await authoritiesStore.getAuthorities()
    mandatesByDeputies.value = await mandatesStore.getMandatesByDeputies()

    const currentDate = new Date().toISOString().split('T')[0]

    const activeDeputies = getActiveDeputiesForSpecificDate(deputies.value, mandates, currentDate)
    hemicycleElements.value = _.map(activeDeputies, deputy => {
      const hemicycleElement: HemicycleElement = {
        deputy: deputy,
        mandateAssembly: getActiveMandatesForSpecificDate(_.filter(mandatesByDeputies.value[deputy.id], mandate => {
          return mandate.authority.authorityType === 'ASSEMBLEE'
        }),
          currentDate
        )[0],
        authorityPG: getAuhtorityPGForSpecificDate(deputy, mandates, currentDate)
      }
      return hemicycleElement
    })
    loadingData.value = false
  }
);

</script>

<template>
  <div v-if="loadingData" class="flex flex-row items-baseline justify-center gap-2 p-8">
    <i class="text-xl text-gray-500 pi pi-spin pi-spinner"></i>
    <p class="text-xl text-gray-500">Loading data</p>
  </div>
  <div v-else>
    <div class="md:h-[60vh] flex justify-center">
      <HemicycleChart :hemicycle-elements="hemicycleElements" :authorities="authorities" />
    </div>
    <div v-if="!_.isEmpty(deputiesStore.deputyIdSelectedOnHemicycle)" class="w-screen flex justify-center">
      <HemicyleElementFullInfo
        :hemicycle-element="_.keyBy(hemicycleElements, hemicycleElement => hemicycleElement.deputy.id)[deputiesStore.deputyIdSelectedOnHemicycle]" />
    </div>
    <div class="w-screen flex flex-wrap gap-8 p-4 justify-center">
      <DeputyCard v-for="deputy in deputies" :key="deputy.id" :deputy="deputy"
        :mandates="mandatesByDeputies[deputy.id]" />
    </div>
  </div>
</template>
