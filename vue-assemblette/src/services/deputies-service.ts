import type { Deputy } from '@/entities/deputy'
import axios from 'axios'
import _ from 'lodash'

const restDeputies = axios.create({
  // better still, use env vars to define your URLs
  baseURL: 'http://localhost:8080/api/deputies',
})

export default class DeputiesService {
  private static deputies: Deputy[]
  public getDeputies = async () => {
    try {
      if (_.isEmpty(DeputiesService.deputies)) {
        return (await restDeputies.get('')).data
      } else {
        return DeputiesService.deputies
      }
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        console.error(err.toJSON())
        throw new Error(err.message)
      } else {
        console.error(err)
        throw new Error('An unexpected error occurred')
      }
    }
  }
}
