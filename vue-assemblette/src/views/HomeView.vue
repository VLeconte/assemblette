<script setup lang="ts">
import { reactive, onMounted, ref } from 'vue';
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

const deputiesStore = useDeputiesStore()
const mandatesStore = useMandatesStore()
const authoritiesStore = useAuthoritiesStore()


const deputies = reactive<{
  data: Deputy[],
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

const mandatesByDeputies = reactive<{
  data: Record<string, Mandate[]>,
  isLoading: boolean
}>({
  data: {},
  isLoading: true
});

const hemicycleElements = reactive<{
  data: HemicycleElement[],
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});
const authorities = ref<Authority[]>([]);

onMounted(
  async () => {
    deputies.data = await deputiesStore.getDeputies()
    deputies.isLoading = false
    mandatesByDeputies.data = await mandatesStore.getMandatesByDeputies()
    mandatesByDeputies.isLoading = false
  }
);

onMounted(
  async () => {
    const deputies = await deputiesStore.getDeputies()
    const mandates = await mandatesStore.getMandates()
    authorities.value = await authoritiesStore.getAuthorities()
    const mandatesByDeputies = await mandatesStore.getMandatesByDeputies()

    const currentDate = new Date().toISOString().split('T')[0]

    const activeDeputies = getActiveDeputiesForSpecificDate(deputies, mandates, currentDate)
    hemicycleElements.data = _.map(activeDeputies, deputy => {
      const hemicycleElement: HemicycleElement = {
        deputy: deputy,
        mandateAssembly: getActiveMandatesForSpecificDate(_.filter(mandatesByDeputies[deputy.id], mandate => {
          return mandate.authority.authorityType === 'ASSEMBLEE'
        }),
          currentDate
        )[0],
        authorityPG: getAuhtorityPGForSpecificDate(deputy, mandates, currentDate)
      }
      return hemicycleElement
    })
    hemicycleElements.isLoading = false
  }
);

</script>

<template>
  <div v-if="!deputies.isLoading && !mandatesByDeputies.isLoading && !hemicycleElements.isLoading">
    <div class="md:h-[60vh] flex justify-center">
      <HemicycleChart :hemicycle-elements="hemicycleElements.data" :authorities="authorities" />
    </div>
    <div class="w-screen flex flex-wrap gap-8 p-4 justify-center">
      <DeputyCard v-for="deputy in deputies.data" :key="deputy.id" :deputy="deputy"
        :mandates="mandatesByDeputies.data[deputy.id]" />
    </div>
  </div>
</template>
